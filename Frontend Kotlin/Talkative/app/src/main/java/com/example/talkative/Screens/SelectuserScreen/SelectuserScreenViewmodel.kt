package com.example.talkative.Screens.SelectuserScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.dataexception.DataOrException
import com.example.talkative.loadingState.LoadingState
import com.example.talkative.model.selectuserResponse.SelectUserResponse
import com.example.talkative.repository.SelectUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectuserScreenViewmodel @Inject constructor(private val repository: SelectUserRepository):ViewModel() {
    var item: SelectUserResponse by mutableStateOf(SelectUserResponse(message = emptyList(), success = false))
    private  val _state = MutableStateFlow(LoadingState.IDLE)
    val state = _state.asStateFlow()

     fun getData(){
        _state.value= LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response= repository.getUser()
                when(response){
                    is DataOrException.Success ->{
                        item=response.data!!
                        if(item.success){
                            _state.value=LoadingState.SUCCESS
                        }
                    }
                    is DataOrException.Error ->{
                        _state.value=LoadingState.FAILED
                    }
                    else ->{
                        _state.value=LoadingState.IDLE
                    }
                }
            }catch (ex:Exception){
                _state.value=LoadingState.FAILED
            }
        }
    }
}