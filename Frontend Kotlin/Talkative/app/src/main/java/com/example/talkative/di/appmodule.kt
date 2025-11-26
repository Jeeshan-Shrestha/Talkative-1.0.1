package com.example.talkative.di

import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import com.example.talkative.DataBase.UserDataBaseDao
import com.example.talkative.DataBase.UserDatabase
import com.example.talkative.cookieManager.AppCookieJar
import com.example.talkative.network.network
import com.example.talkative.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appmodule {

    @Singleton
    @Provides
    fun provideCookieJar(@ApplicationContext context: Context): AppCookieJar {
        return AppCookieJar(context)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(cookieJar: AppCookieJar): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(logging) // Add this interceptor for better debugging
            .build()
    }

    //Retrofit for api request
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): network{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(network::class.java)
    }


    //passing cookies on async image with each request
    @Singleton
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient } // using  client with cookies
            .build()
    }

    //Creating Room DataBase
    @Singleton
    @Provides
    fun ProvideUserDao(userDataBase: UserDatabase): UserDataBaseDao=userDataBase.userDao()

    @Singleton
    @Provides
    fun ProvideAppDataBase(@ApplicationContext context: Context): UserDatabase
    = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_db" )//name of database
        .fallbackToDestructiveMigrationFrom()
        .build()
}