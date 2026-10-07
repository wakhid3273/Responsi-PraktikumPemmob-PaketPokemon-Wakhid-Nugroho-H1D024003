package com.example.pokemon.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton object untuk Retrofit instance
 * Menyediakan configured Retrofit client dengan logging interceptor
 */
object RetrofitInstance {
    
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    
    /**
     * Logging interceptor untuk debugging network request/response
     * Log level BODY untuk melihat detail request dan response
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    /**
     * OkHttp client dengan logging interceptor dan timeout configuration
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    /**
     * Retrofit instance dengan Gson converter untuk JSON parsing
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * API service instance
     * Lazy initialization untuk efisiensi memory
     */
    val api: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}
