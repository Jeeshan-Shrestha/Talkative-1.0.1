package com.example.talkative.screens.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetHomeFeedResponse.HomeFeedResponse
import com.example.talkative.repository.HomeFeedRepository
import com.example.talkative.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(private val repository: HomeFeedRepository): ViewModel() {

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    var item: HomeFeedResponse by mutableStateOf(HomeFeedResponse(
        message = emptyList(),
        success = false
    ))

    init {
        Homefeed()
    }

    fun Homefeed(){
        getHomeFeed()
    }

     private  fun getHomeFeed() {
        viewModelScope.launch {
            _state.value= LoadingState.LOADING

            try{
                val response = repository.HomeFeed()

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