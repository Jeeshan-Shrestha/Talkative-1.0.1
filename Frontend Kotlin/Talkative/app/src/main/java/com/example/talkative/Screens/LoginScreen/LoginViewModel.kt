package com.example.talkative.screens.LoginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.LoginResponse.LoginResponse
import com.example.talkative.repository.LoginRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository): ViewModel() {

    var item : LoginResponse by mutableStateOf(LoginResponse(success = false, message = ""))
    var isLoading: Boolean by mutableStateOf(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    //handling cookie checking etc
    var isLoggined by mutableStateOf(false)

    fun checkStatusLoginStatus(domain:String){
        val cookies = repository.getStoredCookies(domain)
        isLoggined = cookies.any{it.contains("token")}
    }

    //login user
    fun LoginUser(email: String, password: String,furtherAction:()->Unit) {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            if(email.isEmpty() || password.isEmpty()){
                return@launch
            }
            try {
                val response = repository.loginUser(email,password)
                when(response){

                    is DataorException.Success->{

                        item=response.data!!

                        if(item.success == true){

                            _state.value= LoadingState.SUCCESS

                            furtherAction()

                            isLoading=false
                        }
                    }
                    is DataorException.Error->{
                        _state.value= LoadingState.FAILED
                        _state.value.message=response.message
                        //  Log.d("april", "error: ${response.message} ")
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