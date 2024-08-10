package com.axtech.appmanager.data.client

import com.axtech.appmanager.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory

object RetrofitFactory {

    fun get(authorizationInterceptor: AuthorizationInterceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .addInterceptor(authorizationInterceptor)
                    .build()
            )
            .build()

    fun <T> createService(retrofit: Retrofit, clazz: Class<T>): T {
        return retrofit.create(clazz) as T
    }
}