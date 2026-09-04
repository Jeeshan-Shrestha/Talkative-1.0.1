package com.example.talkative.screens.SelectPeopleToChatWithScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.ChatScreenReqAndRes.Message
import com.example.talkative.network.WebSocketManager
import com.example.talkative.repository.MessageHistoryRepository
import com.example.talkative.utils.ChatTimeUtils
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val messageHistoryRepository: MessageHistoryRepository
) : ViewModel() {


    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    //Loading state of the message history request
    private val _historyState = MutableStateFlow(LoadingState.IDLE)
    val historyState = _historyState.asStateFlow()

    val status = webSocketManager.status

    //For filtering chat from other users
    private var chatPartner: String? = null
    private var ownUsername: String? = null

    //keeps a handle on the running history + observe coroutine so re-init cancels the old one
    private var chatJob: Job? = null

    //Initialize message collection: history first, then live websocket messages
    fun initChat(chatPartner: String, ownUsername: String) {
        Log.d("ChatHistory", "initChat(chatPartner=$chatPartner, ownUsername=$ownUsername)")
        //already initialised for this exact conversation, nothing to do
        if (this.chatPartner == chatPartner &&
            this.ownUsername == ownUsername &&
            _historyState.value.status == LoadingState.Status.SUCCESS
        ) {
            return
        }

        this.chatPartner = chatPartner
        this.ownUsername = ownUsername
        resetMessages()

        chatJob?.cancel()
        chatJob = viewModelScope.launch {
            loadHistory(chatPartner = chatPartner, ownUsername = ownUsername)
            observeIncomingMessage()
        }
        connect()
    }

    fun connect() {
        webSocketManager.connect()
    }

    fun sendMesage(content: String, receiver: String) {

        val target = chatPartner ?: return //getting receiver
        webSocketManager.sendMessage(content = content, receiver = target)

        //add sent message to ui with the current time
        val sentMessage = Message.Sent(content = content, timestamp = ChatTimeUtils.nowIso())
        _messages.value = _messages.value + sentMessage
    }

    //Fetches the whole history for the logged in user and keeps only this conversation
    private suspend fun loadHistory(chatPartner: String, ownUsername: String) {
        _historyState.value = LoadingState.LOADING

        //backend's "receiver" query param means the chat partner: it returns the
        //full two way conversation between the logged in user and that person
        when (val response = messageHistoryRepository.getHistory(receiver = chatPartner)) {
            is DataorException.Success -> {
                val allItems = response.data?.message.orEmpty()
                val history = allItems
                    .filter { item ->
                        (item.sender == chatPartner && item.receiver == ownUsername) ||
                                (item.sender == ownUsername && item.receiver == chatPartner)
                    }
                    .sortedWith(compareBy(nullsFirst()) { it.timestamp })
                    .map { item ->
                        if (item.sender == ownUsername) {
                            Message.Sent(content = item.content, timestamp = item.timestamp)
                        } else {
                            Message.Received(
                                sender = item.sender,
                                content = item.content,
                                timestamp = item.timestamp
                            )
                        }
                    }

                Log.d(
                    "ChatHistory",
                    "history: total=${allItems.size} matchedThisChat=${history.size}"
                )

                //history goes first, any live message that slipped in stays after it
                _messages.value = history + _messages.value
                _historyState.value = LoadingState.SUCCESS
            }

            is DataorException.Error -> {
                Log.e("ChatHistory", "history load error: ${response.message}")
                _historyState.value = LoadingState.FAILED
                _historyState.value.message = response.message
            }

            else -> {
                _historyState.value = LoadingState.IDLE
            }
        }
    }

    private suspend fun observeIncomingMessage() {
        webSocketManager.incommingMessages.collect { msg ->
            //Showing messages only for this chat
            if (msg.sender == chatPartner || msg.receiver == chatPartner) {
                val received = Message.Received(
                    sender = msg.sender,
                    content = msg.content,
                    timestamp = ChatTimeUtils.nowIso()
                )
                _messages.value = _messages.value + received
            }
        }
    }

    override fun onCleared() {
        webSocketManager.disconnect()
        super.onCleared()
    }

    fun resetMessages() {
        _messages.value = emptyList()
    }
}
