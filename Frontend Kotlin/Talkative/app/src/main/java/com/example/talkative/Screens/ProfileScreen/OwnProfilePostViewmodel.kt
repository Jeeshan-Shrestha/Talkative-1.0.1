package com.example.talkative.screens.ProfileScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.FollowUnfollowResponse.FollowUnFollowResponse
import com.example.talkative.model.OwnProfileResponse.Message
import com.example.talkative.model.OwnProfileResponse.OwnProfileResponse
import com.example.talkative.model.OwnProfileResponse.Post
import com.example.talkative.repository.FollowUnFollowRepository
import com.example.talkative.repository.OwnProfilePostRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class OwnProfilePostViewmodel @Inject constructor(private val repository: OwnProfilePostRepository): ViewModel() {
    var item : OwnProfileResponse by mutableStateOf(OwnProfileResponse(message =
        Message(
             avatar="",
         bio="",
         coverPhoto="",
     displayName="",
     followers=emptyList(),
     followersCount=0,
     following=emptyList(),
     followingCount=0,
     posts=emptyList(), numberOfPosts = 0, joinDate = "",
     username=""
        ), success = false))
    var isLoading: Boolean by mutableStateOf(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    init {
        OwnprofilePost()
    }

    //login user
    fun OwnprofilePost() {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            try {

                val response = repository.OwnProfilePost()

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