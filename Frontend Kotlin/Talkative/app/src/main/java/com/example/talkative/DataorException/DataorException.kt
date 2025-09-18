package com.example.talkative.DataorException

sealed class DataorException<T>(
    val data:T?=null, // we will get Datafrom API
    val message:String?=null){
    class Success<T>(data: T?):DataorException<T>(data)
    class Error<T>(message:String?,data: T?=null): DataorException<T>(data,message)
    class Loading<T>(data: T?=null):DataorException<T>(data)
}