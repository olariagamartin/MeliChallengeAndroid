package com.themarto.core.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.mercadolibre.com/"
private const val AUTH_TOKEN = "APP_USR-7001860561598073-021713-d5628fa10875264dd73bd5520b20e244-177748327"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(createAuthInterceptor())
    .build()

fun createAuthInterceptor(): Interceptor {
    return Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(newRequest)
    }

}


fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

}