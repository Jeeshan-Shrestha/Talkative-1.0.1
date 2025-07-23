package com.example.talkative.network

import android.util.Log
import com.example.talkative.model.MessageResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Singleton

@Singleton
class  WebSocketManager{
    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket

    private val _messages = MutableSharedFlow<String>(replay = 10)
    val messages = _messages.asSharedFlow()

    private val _status = MutableStateFlow("Connecting...")
    val status=_status.asStateFlow()

    fun connect(url:String){
        val request = Request.Builder().url(url)
            .build()
        webSocket= client.newWebSocket(request, socketListner)
    }
    fun sendMessage(message:String){
        webSocket.send(message)
    }
    fun close(){
        webSocket.close(1000,"Normal Closure")
    }

    private val socketListner = object:WebSocketListener(){

        override fun onOpen(webSocket: WebSocket,response: Response){
            Log.d("WebSocket", "Connected")
            _status.tryEmit("Connected")
            Log.d("WebSocket", "${response.body} ")
        }
        override fun onMessage(webSocket: WebSocket,text:String){
            Log.d("WebSocket", "Received: $text")
            _messages.tryEmit(text)
            Log.d("WebSocket",_messages.toString())

        }

        override fun onClosing(webSocket: WebSocket,code:Int,reason:String){
            Log.d("WebSocket", "Closing: $reason")
            _status.tryEmit("Closing.......")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("WebSocket", "Closed: $reason")
            _status.tryEmit("Closed")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("WebSocket", "Error: ${t.message}")
            _status.tryEmit("Connection Failed ${t.message}")
        }
    }
}

