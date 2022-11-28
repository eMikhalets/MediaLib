package com.emikhalets.medialib.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*
import javax.inject.Inject

class RetrofitFactory @Inject constructor() {

    private val timeout: Long = 15

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val loggerInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiKeyInterceptor: Interceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url
        val newUrl = url.newBuilder().addQueryParameter("apikey", MOVIES_API_KEY).build()
        val newRequest = request.newBuilder().url(newUrl).build()
        chain.proceed(newRequest)
    }

    private fun client(): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(loggerInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(MOVIES_BASE_URL)
        .client(client().build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    companion object {
        const val MOVIES_BASE_URL = "https://www.omdbapi.com/"
        private const val MOVIES_API_KEY = "fb3ce40d"
    }
}