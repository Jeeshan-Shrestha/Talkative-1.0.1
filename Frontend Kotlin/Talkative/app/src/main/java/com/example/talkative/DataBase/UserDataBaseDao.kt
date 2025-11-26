package com.example.talkative.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataBaseDao {

    //get every thing from database
    @Query("SELECT * from userInfo_tbl")
    fun getUserDetails(): Flow<UserInformation?> //nullable because when user logouts we erase details

    //if we want to insert Something
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(userInfo: UserInformation)

    //we want to updateSomething
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(userInfo: UserInformation)

    //Delete DataBase
    @Query("DELETE from userInfo_tbl")
    suspend fun deleteAll()

}