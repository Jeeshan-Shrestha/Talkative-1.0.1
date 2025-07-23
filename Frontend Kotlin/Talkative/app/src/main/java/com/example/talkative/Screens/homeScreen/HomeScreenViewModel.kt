package com.example.talkative.Screens.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.model.Message
import com.example.talkative.model.MessageResponse
import com.example.talkative.model.sendmsgRequest.SendmsgReq
import com.example.talkative.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val json = Json { ignoreUnknownKeys = true }

    // For current UI screen
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _status = MutableStateFlow("Connecting.....")
    val status = _status.asStateFlow()

    private var myUsername: String? = null
    private var currentRecipient: String? = null
    private var lastSentMessage: String? = null

    // All messages per user
    private val allMessages = mutableMapOf<String, MutableList<Message>>()

    fun ConnectAndObserve(username: String) {
        myUsername = username
        repository.connectSocket(username = username)

        // Clear previous messages in UI
        //_messages.value = emptyList()

        viewModelScope.launch {
            repository.observeStatus().collect { st ->
                Log.d("April", "Status update: $st")
                _status.value = st
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.observeMessages().collect { msg ->
                try {
                    val incoming = json.decodeFromString<MessageResponse>(msg)
                    val sender = incoming.sender.trim()
                    val receiver = incoming.receiver.trim()
                    val content = incoming.content.trim()

                    Log.d("pikachu", "data received from backend $content ")
                    // Determine the other user in the conversation
                    val otherUser = if (sender == myUsername) receiver else sender

                    // Check if message is not an echo
                    val isEcho = sender == myUsername && content == lastSentMessage
                    if (!isEcho) {
                        val newMsg = Message.Recieved(sender = sender, text = content, receiver = receiver)

                        // Save to allMessages
                        val list = allMessages.getOrPut(otherUser) { mutableListOf() }
                        list.add(newMsg)

                        // Show on screen only if current chat matches
                        if (otherUser == currentRecipient) {
                            Log.d("pikachu", " ${_messages.value} ")
                            _messages.update { it + newMsg }
                        }
                    }

                } catch (e: Exception) {
                    Log.e("Sans", "Message parsing error: ${e.message}")
                } finally {
                    lastSentMessage = null
                }
            }
        }
    }

    fun switchRecipient(recipient: String) {
        currentRecipient = recipient
        _messages.value = allMessages[recipient] ?: emptyList()
    }

    fun SendMessages(msg: String, recipientUsername: String) {
        if (msg.isNotBlank()) {
            lastSentMessage = msg.trim()
            currentRecipient = recipientUsername

            // Add to current chat UI
            _messages.update { currentList ->
                currentList + Message.Sent(lastSentMessage!!)
            }

            // Also store in allMessages
            val list = allMessages.getOrPut(recipientUsername) { mutableListOf() }
            list.add(Message.Sent(lastSentMessage!!))

            val fullMsg = SendmsgReq(content = lastSentMessage!!, receiver = recipientUsername)
            val jsonMsg = Json.encodeToString(fullMsg)
            Log.d("akriti", "Sending: $jsonMsg")
            repository.sendMessage(jsonMsg)
        }
    }

    fun clearMessages() {
        Log.d("allout", "Clearing messages for UI")
        _messages.value = emptyList()
        lastSentMessage = null
    }

    fun disconnect() {
       clearMessages()
        repository.closeSocket()
    }

    override fun onCleared() {
        super.onCleared()
        repository.closeSocket()
    }
}
