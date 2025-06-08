package com.example.talkative.Screens.addUsername

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.dataexception.DataOrException
import com.example.talkative.loadingState.LoadingState
import com.example.talkative.model.loginRequest.LoginResponse
import com.example.talkative.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository:LoginRepository):ViewModel() {

        var item:LoginResponse by mutableStateOf(LoginResponse(message = "", success = false))

        private val _state= MutableStateFlow(LoadingState.IDLE)
    val state= _state.asStateFlow()

    fun loginUser(Username:String,Password:String,furtherAction:()->Unit){
        _state.value=LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO){
            if(Username.isEmpty() || Password.isEmpty()){
                return@launch
            }
            try {
                val response= repository.loginUser(username = Username,Password=Password)
                when(response){
                    is DataOrException.Success ->{
                        item=response.data!!
                        Log.d("April", "loginUser: $item ")
                        if(item.success){
                            _state.value=LoadingState.SUCCESS
                            withContext(Dispatchers.Main) {
                                furtherAction()
                            }

                        }
                    }
                    is DataOrException.Error ->{
                        _state.value=LoadingState.FAILED
                        _state.value.message = response.message ?: "An error occurred"
                    }
                    else ->{
                        _state.value=LoadingState.IDLE
                    }
                }

            }catch (e:Exception){
                _state.value= LoadingState.FAILED
            }
        }
    }
}