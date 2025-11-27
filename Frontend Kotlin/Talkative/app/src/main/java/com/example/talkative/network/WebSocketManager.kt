package com.example.talkative.network

import android.util.Log
import com.example.talkative.model.ChatScreenReqAndRes.ChatMessage
import com.example.talkative.model.ChatScreenReqAndRes.SendMessage
import com.example.talkative.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebSocketManager @Inject constructor(
    private val okHttpClient: OkHttpClient): WebSocketListener(){

        private var webSocket: WebSocket?=null

    //Receiving message as flow
    private val _incommingMessages = MutableSharedFlow<ChatMessage>(replay = 10)
    val incommingMessages = _incommingMessages.asSharedFlow()

    //Status of webSocket
    private val _status= MutableStateFlow("Disconnected")
    val status = _status.asStateFlow()

    private var isConnected=false

    private val gson = Gson()

    fun connect(){

        if(isConnected){
            return
        }
        val request = Request.Builder()
            .url(Constants.web_Socket)
            .build()
        _status.value="Connecting....."
        webSocket = okHttpClient.newWebSocket(request,this)
    }

    //Send messages
    fun sendMessage(content: String,receiver: String){
        val msg= SendMessage(content = content, receiver = receiver)
        val json= gson.toJson(msg) //converting to json
        webSocket?.send(json)
    }

    fun disconnect(){
        webSocket?.close(1000,"Closed by User")
        isConnected=false
        _status.value = "Disconnected"
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        isConnected=true
        _status.tryEmit("Connected")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        _incommingMessages.tryEmit(text)
        try {
            Log.d("akriti", "onMessage: $text ")
            val message=gson.fromJson(text, ChatMessage::class.java)
            _incommingMessages.tryEmit(message)
        }catch (ex: Exception){
            Log.d("akriti", "from catch block error: ${ex.message}")
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        isConnected=false
        _status.tryEmit("Closing.....")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        isConnected=false
        _status.tryEmit("Closed")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        _status.tryEmit("Connection Failed")
        Log.d("WebSocket", "failedd connecting n${t.message}")
    }


}