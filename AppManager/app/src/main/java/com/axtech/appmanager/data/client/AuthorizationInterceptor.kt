package com.axtech.appmanager.data.client

import android.content.Context
import com.axtech.appmanager.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder()
            .addHeader("Client-Key", BuildConfig.CLIENT_KEY)
            .addHeader("Accept-Language", "en")
            .build())
    }
}
