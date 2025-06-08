package com.example.talkative.di
import com.example.talkative.network.Net
import com.example.talkative.network.WebSocketManager
import com.example.talkative.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWebSocketManager():WebSocketManager{
        return WebSocketManager()
    }

    @Provides
    @Singleton
    fun provideRetrofit():Net{
        return  Retrofit.Builder()
            .baseUrl(Constants.MainUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Net::class.java) //hello world
    }

}