package com.radustavila.currencyexchangerates.network

import com.radustavila.currencyexchangerates.model.Exchange
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://0gzg3.mocklab.io/json/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CurrencyExchangeApiService {

    @GET("1")
    suspend fun getExchangeObject(): Exchange
}

object CurrencyExchangeApi {
    val retrofitService: CurrencyExchangeApiService by lazy { retrofit.create(CurrencyExchangeApiService::class.java) }
}
