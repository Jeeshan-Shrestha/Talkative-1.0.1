package com.example.talkative.screens.ShowFollowersandFollowingScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetFollowersResponse.GetFollowersResponse
import com.example.talkative.repository.GetFollowersRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetFollowersViewModel @Inject constructor(private val repository: GetFollowersRepository):ViewModel() {

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    var item : GetFollowersResponse  by mutableStateOf(
        GetFollowersResponse(
            message = emptyList(),
            success = false
        )
    )


    fun getFollowers(username:String) {
        viewModelScope.launch {
            _state.value= LoadingState.LOADING

            try{
                val response = repository.getFollowers(username=username)

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
