package com.example.talkative.screens.ProfileScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.viewOthersProfile.Message
import com.example.talkative.model.viewOthersProfile.OtherUsreProfileResponse
import com.example.talkative.repository.OtherUserProfileRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int
import kotlin.String

@HiltViewModel
class OtherUserProfileViewModel @Inject constructor(private val otherUserProfileRepository: OtherUserProfileRepository):
    ViewModel(){

        var item : OtherUsreProfileResponse by mutableStateOf(OtherUsreProfileResponse(
            message= Message(
                avatar="",
            bio="",
             coverPhoto= "",
     displayName="",
     following =false,
     followersCount=0,
     followingCount=0,
     joinDate="",
     numberOfPosts=0,
     posts=emptyList(),
     username = ""),
            success = false
        ))

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    fun OtherUserProfile(username:String) {
        viewModelScope.launch {
            _state.value= LoadingState.LOADING

            try{
                val response = otherUserProfileRepository.OtherUserProfile(username=username)

                when(response) {
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
                }catch (ex: Exception) {
                _state.value = LoadingState.FAILED
            }
        }
    }
}