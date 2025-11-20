package com.example.talkative.cookieManager

import android.content.Context
import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

//this class is used to save and load cookeis

class AppCookieJar(context: Context) : CookieJar {
    private val cookiePreferences = CookiePreferences(context)
    private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies.toMutableList()
        cookiePreferences.saveCookies(cookies) // Saves to SharedPreferences
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = cookiePreferences.loadCookies()
        Log.d("CookieJar", "Sending cookies for ${url.host}: $cookies")
        return cookiePreferences.loadCookies() // Loads from SharedPreferences
    }

    fun clearCookies() {
        cookieStore.clear()
        cookiePreferences.clearCookies()
    }
}