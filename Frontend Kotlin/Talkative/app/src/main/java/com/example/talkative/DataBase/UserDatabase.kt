package com.example.talkative.DataBase

import android.R.attr.version
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities =[UserInformation::class],version=1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDataBaseDao
}