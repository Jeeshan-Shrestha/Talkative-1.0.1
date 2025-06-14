package com.example.talkative.Screens.signupScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.talkative.dataexception.DataOrException
import com.example.talkative.loadingState.LoadingState
import com.example.talkative.model.registerRequest.RegisterResponse
import com.example.talkative.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: RegisterRepository):ViewModel(){
    var item:RegisterResponse by mutableStateOf(RegisterResponse(message = "", success = false))
    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    fun registerUser(Username:String,Password:String,furthurAction:()-> Unit){
        _state.value=LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            if(Username.isEmpty() || Password.isEmpty())
                return@launch
            try {
                val response = repository.RegisterUser(username = Username, password = Password)
                when(response){

                    is DataOrException.Success -> {
                        item = response.data!!
                        if(item.success){
                            _state.value=LoadingState.SUCCESS
                            withContext(Dispatchers.Main){
                                furthurAction()
                            }
                        }
                    }
                    is DataOrException.Error->{
                        _state.value = LoadingState.FAILED
                        _state.value.message=response.message ?:"An error Occured"
                    }
                    else->{
                        _state.value=LoadingState.IDLE
                    }
                }
            }catch (e:Exception){
                _state.value=LoadingState.FAILED
                _state.value.message=e.message.toString()
            }
        }
    }

}