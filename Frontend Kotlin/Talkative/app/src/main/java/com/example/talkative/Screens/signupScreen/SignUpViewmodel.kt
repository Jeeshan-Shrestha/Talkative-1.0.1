package com.example.talkative.screens.SignupScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.LoginResponse.LoginResponse
import com.example.talkative.model.SignupResponse.SignupResponse
import com.example.talkative.repository.SignupRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewmodel @Inject constructor(private val repository: SignupRepository): ViewModel(){

    var item : SignupResponse by mutableStateOf(SignupResponse(success = false, message = ""))
    var isLoading: Boolean by mutableStateOf(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()


    //login user
    fun SignupUser(email: String, username: String, password: String, furtherAction:()->Unit) {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            if(email.isEmpty() || password.isEmpty() || username.isEmpty()){
                return@launch
            }
            try {
                val response = repository.SignupUser(email = email, username = username, password = password)
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