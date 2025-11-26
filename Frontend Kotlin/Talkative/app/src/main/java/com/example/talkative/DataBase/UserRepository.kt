package com.example.talkative.DataBase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDatabaseDao: UserDataBaseDao) {

    suspend fun insertUserInfo(user: UserInformation) = userDatabaseDao.insertDetails(userInfo = user)
    suspend fun updateUserInfo(user: UserInformation)=userDatabaseDao.update(userInfo = user)
    suspend fun deleteAll()=userDatabaseDao.deleteAll()

    fun getUserDetails(): Flow<UserInformation?> = userDatabaseDao.getUserDetails().flowOn(
        Dispatchers.IO).conflate()

}