package com.example.talkative.screens.CreatePost

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.CreatePostResponse.CreatePostResponse
import com.example.talkative.repository.CreatePostRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewmodel  @Inject constructor(
    application: Application,
    private  val createPostRepository: CreatePostRepository): AndroidViewModel(application){

        var item: CreatePostResponse by mutableStateOf(
            CreatePostResponse(message = "", success = false)
        )
    private val _state= MutableStateFlow(LoadingState.IDLE)
    val state = _state.asStateFlow()

    fun PostImage(caption: String,imageUri: Uri,onSuccess:()-> Unit){

        viewModelScope.launch {
            _state.value = LoadingState.LOADING
            try {
                val response = createPostRepository.CreatePost(content = caption,
                    imageUri = imageUri, context = getApplication())

                when(response){
                    is DataorException.Success<*> -> {
                        item=response.data!!

                        if(item.success == true){
                            _state.value = LoadingState.SUCCESS
                            onSuccess.invoke()
                        }
                    }
                    is DataorException.Error ->{
                        _state.value= LoadingState.FAILED
                    }
                    else -> {
                        _state.value= LoadingState.FAILED
                    }
                }
            }catch (ex:Exception){
                _state.value = LoadingState.FAILED
            _state.value.message = ex.message
        }
        }
    }
}