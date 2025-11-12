package com.example.fittrack.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ZenQuotesApi {
    @GET("api/random")
    suspend fun getRandomQuote(): List<QuoteResponse>

    companion object {
        fun create(): ZenQuotesApi {
            return Retrofit.Builder()
                .baseUrl("https://zenquotes.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ZenQuotesApi::class.java)
        }
    }
}

data class QuoteResponse(
    val q: String,
    val a: String
)
