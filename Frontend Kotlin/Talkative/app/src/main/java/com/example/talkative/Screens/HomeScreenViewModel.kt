package com.example.talkative.Screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.model.Message
import com.example.talkative.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: ChatRepository):ViewModel() {

    private val _messages= MutableStateFlow<List<Message>>(emptyList())
    val messages=_messages.asStateFlow()

    private val _status=MutableStateFlow("Connecting.....")
    val status=_status.asStateFlow()

    // Add a check to prevent adding echoed messages you sent
    private var lastSentMessage: String? = null


    init {
        ConnectAndObserve()

    }

    fun ConnectAndObserve(){
        repository.connectSocket()

        viewModelScope.launch{
            repository.observeStatus().collect{st->
                Log.d("April", "${st} ")
                _status.value=st
            }
        }

        viewModelScope.launch {
            repository.observeMessages().collect{msg->
                if (msg != lastSentMessage) {
                    Log.d("April", "ConnectAndObserve:${msg} ")
                    _messages.update { currentList ->
                        currentList + Message.Recieved(msg)
                    }
                }
                lastSentMessage = null
            }
        }

    }

    fun SendMessages(msg:String){
        if(msg.isNotBlank()){
            lastSentMessage=msg
            _messages.update {currentList->
                currentList + Message.Sent(msg)
            }
            repository.sendMessage(msg)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.closeSocket()
    }

}
