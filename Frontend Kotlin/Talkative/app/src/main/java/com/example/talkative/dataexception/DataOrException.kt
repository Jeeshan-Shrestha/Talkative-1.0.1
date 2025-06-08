package com.example.talkative.dataexception

sealed class DataOrException<T>(
    val data:T?=null,  // We will Get Data When We Hit API Successfully
    val message:String?=null){
        class Success<T>(data:T?):DataOrException<T>(data)
        class Error<T>(message:String?,data:T?=null):DataOrException<T>(data,message)
        class Loading<T>(data:T?=null):DataOrException<T>(data)
}

