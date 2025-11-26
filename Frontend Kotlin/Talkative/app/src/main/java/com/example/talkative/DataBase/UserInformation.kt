package com.example.talkative.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//this is the blueprint for our database, which indicates the thing that it will contain
@Entity(tableName = "userInfo_tbl")
data class UserInformation(
    @PrimaryKey
    @ColumnInfo(name="user_name")
    val username: String,

    @ColumnInfo(name="display_name")
    val displayName:String,

    @ColumnInfo(name="userPhoto")
    val avatar:String?
)