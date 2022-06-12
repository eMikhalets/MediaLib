package com.emikhalets.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.*

internal const val MOVIES_BASE_URL = "https://api.themoviedb.org/3"

class RetrofitFactory(
    private val baseUrl: String,
) {
    private val timeout: Long = 30

    private val contentType: MediaType = "application/json".toMediaType()

    private fun json(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private fun loggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun client(): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(loggerInterceptor())
        .readTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)

    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client().build())
        .addConverterFactory(json().asConverterFactory(contentType))
        .build()
}