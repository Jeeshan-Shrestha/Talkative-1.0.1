package com.example.talkative.screens.searchScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.FollowUnfollowResponse.FollowUnFollowResponse
import com.example.talkative.repository.FollowUnFollowRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowUnFollowViewModel @Inject constructor(private val repository: FollowUnFollowRepository): ViewModel() {
    var item : FollowUnFollowResponse by mutableStateOf(FollowUnFollowResponse(message ="", success = false))
    var isLoading: Boolean by mutableStateOf(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()


    //login user
    fun FollowUnfollowuser(username:String) {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            try {
                val response = repository.FollowUnFollowUser(username = username)
                when(response){

                    is DataorException.Success->{

                        item=response.data!!

                        if(item.success == true){

                            _state.value= LoadingState.SUCCESS

                            isLoading=false
                        }
                    }
                    is DataorException.Error->{
                        _state.value= LoadingState.FAILED
                        _state.value.message=response.message
                        //Log.d("april", "error: ${response.message} ")
                        isLoading=false
                    }
                    else ->{
                        _state.value= LoadingState.IDLE
                        isLoading=false
                    }
                }
            } catch (e: Exception) {
                isLoading=false
            }
        }
    }
}