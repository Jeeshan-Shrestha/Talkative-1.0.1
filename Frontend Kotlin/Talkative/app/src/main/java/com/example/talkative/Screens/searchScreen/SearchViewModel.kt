package com.example.talkative.screens.searchScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.SearchResponse.SearchResponse
import com.example.talkative.model.SignupResponse.SignupResponse
import com.example.talkative.repository.SearchUserRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchUserRepository): ViewModel() {
    var item : SearchResponse by mutableStateOf(SearchResponse(message = emptyList(), success = false))
    var isLoading: Boolean by mutableStateOf(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()


    //login user
    fun SearchUser(username:String) {
        viewModelScope.launch {

            _state.value= LoadingState.LOADING

            try {
                val response = repository.SearchUser(username = username)
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
