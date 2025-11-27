package com.example.talkative.screens.SelectPeopleToChatWithScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.model.ChatScreenReqAndRes.ChatMessage
import com.example.talkative.model.ChatScreenReqAndRes.Message
import com.example.talkative.network.WebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val webSocketManager: WebSocketManager): ViewModel() {


    private val _messages= MutableStateFlow<List<Message>>(emptyList())
    val messages=_messages.asStateFlow()


    val status=webSocketManager.status

    //For filtering chat from other users
    private var chatPartner:String?=null
    private var ownUsername:String?=null

    //Initialize MessageCollection
    fun initChat(chatPartner:String,ownUsername:String){
        this.chatPartner=chatPartner
        this.ownUsername=ownUsername
        resetMessages()
        observeIncommingMessage()
        connect()
    }

    fun connect(){
        webSocketManager.connect()
    }

    fun sendMesage(content: String,receiver: String){

        val receiver= chatPartner?:return //getting reciever
        webSocketManager.sendMessage(content = content, receiver = receiver)
            //add sent message to ui
        val sentmessage= Message.Sent(content = content)
        _messages.value=_messages.value + sentmessage

    }

    private fun observeIncommingMessage(){
        viewModelScope.launch {
            webSocketManager.incommingMessages.collect { msg ->
                //Showing messages only for this Chat
                if (msg.sender == chatPartner || msg.receiver == chatPartner) {
                    val received = Message.Received(
                        sender = msg.sender,
                        content = msg.content
                    )
                    _messages.value = _messages.value + received
                }
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