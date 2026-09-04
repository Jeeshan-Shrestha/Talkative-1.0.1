package com.example.talkative.repository

import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.ChatScreenReqAndRes.MessageHistoryResponse
import com.example.talkative.network.network
import javax.inject.Inject

class MessageHistoryRepository @Inject constructor(private val network: network) {

    suspend fun getHistory(receiver: String): DataorException<MessageHistoryResponse> {
        return try {
            val response = network.getMessageHistory(receiver = receiver)
            Log.d("ChatHistory", "getHistory($receiver) -> success=${response.success} count=${response.message.size}")
            DataorException.Success(data = response)
        } catch (ex: Exception) {
            Log.e("ChatHistory", "getHistory($receiver) failed: ${ex.message}", ex)
            DataorException.Error(message = ex.message.toString())
        }
    }
}
