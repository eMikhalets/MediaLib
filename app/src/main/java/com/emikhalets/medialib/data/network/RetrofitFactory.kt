package com.emikhalets.medialib.data.network

import com.emikhalets.medialib.utils.AppPrefs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*
import javax.inject.Inject

private const val MOVIES_BASE_URL = "https://api.themoviedb.org/3"
private const val MOVIES_API_KEY = "8da3652ecf7f31da7ee6027aa3aa8b53"

class RetrofitFactory @Inject constructor(private val prefs: AppPrefs) {

    private val timeout: Long = 30

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val loggerInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val requestInterceptor: Interceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url
        val newUrl = url.newBuilder()
            .addQueryParameter("api_key", MOVIES_API_KEY)
            .addQueryParameter("language", prefs.appLanguage)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }

    private fun client(): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(loggerInterceptor)
        .addInterceptor(requestInterceptor)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(MOVIES_BASE_URL)
        .client(client().build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}