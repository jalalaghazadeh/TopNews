package com.mrjalal.topnews.di.network.module

import com.mrjalal.topnews.di.network.qualifier.ApiKeyQualifier
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkHeaderInterceptor @Inject constructor(
    @ApiKeyQualifier private val apiKey: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()

        newRequest.addHeader("Accept", "application/json")
        newRequest.addHeader("Authorization", apiKey)

        return chain.proceed(newRequest.build())
    }
}