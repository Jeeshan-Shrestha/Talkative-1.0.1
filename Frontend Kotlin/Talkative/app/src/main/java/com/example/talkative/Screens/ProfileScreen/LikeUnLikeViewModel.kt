package com.example.talkative.screens.ProfileScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.PostLikeResponse.PostLikeResponse
import com.example.talkative.repository.LikedUnlikeRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeUnLikeViewModel @Inject constructor(private val likeunlikerepo: LikedUnlikeRepository):ViewModel() {

    var item : PostLikeResponse by mutableStateOf(PostLikeResponse(message ="", success = false))

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()


    //login user
    fun LikeUnLikePost(id:String,username:String) {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            try {
                val response = likeunlikerepo.LikeunLike(id=id,username = username)
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

