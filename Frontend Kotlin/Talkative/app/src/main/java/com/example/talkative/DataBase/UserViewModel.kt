package com.example.talkative.DataBase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val _userDetails= MutableStateFlow<UserInformation?>(null)

    val userDetails = _userDetails.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserDetails()
                .distinctUntilChanged()
                .collect { user->
                    if(user?.username.isNullOrEmpty()){
                        //empty
                    }else{
                        _userDetails.value=user
                    }
                }
        }
    }

    fun addUserDetails(user: UserInformation) =viewModelScope.launch {
        repository.insertUserInfo(user = user)
    }

    fun updateUserDetails(user: UserInformation)=viewModelScope.launch {
        repository.updateUserInfo(user = user)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }


}