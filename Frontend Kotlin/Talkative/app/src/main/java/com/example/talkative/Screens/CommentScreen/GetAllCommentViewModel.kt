package com.example.talkative.screens.CommentScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetAllCommentResponse.Comment
import com.example.talkative.repository.GetAllCommentRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllCommentViewModel @Inject constructor(private val repository: GetAllCommentRepository): ViewModel() {

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    var item :List<Comment> by mutableStateOf(emptyList())

    fun getAllComments(id:String) {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            try {
                val response = repository.getAllComment(postId =id)
                when(response){

                    is DataorException.Success->{

                        item=response.data!!
                        _state.value= LoadingState.SUCCESS


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