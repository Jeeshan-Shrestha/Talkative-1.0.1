package com.example.talkative.screens.ProfileScreen

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.DeletePostResponse.DeletePostResponse
import com.example.talkative.model.PostLikeResponse.PostLikeResponse
import com.example.talkative.repository.DeletePostRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletePostViewModel @Inject constructor(private val repository: DeletePostRepository): ViewModel() {
    var item : DeletePostResponse by mutableStateOf(DeletePostResponse(message ="", success = false))

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()


    //login user
    fun DeletePost(id:String){

        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            try {
                val response =  repository.deletePost(postId = id)
                when(response){

                    is DataorException.Success->{

                        item=response.data!!

                        if(item.success == true){

                            _state.value= LoadingState.SUCCESS

                        }
                    }
                    is DataorException.Error->{
                        _state.value= LoadingState.FAILED
                        _state.value.message=response.message
                        //Log.d("april", "error: ${response.message} ")

                    }
                    else ->{
                        _state.value= LoadingState.IDLE

                    }
                }
            } catch (e: Exception) {
                _state.value= LoadingState.FAILED
            }
        }
    }
}