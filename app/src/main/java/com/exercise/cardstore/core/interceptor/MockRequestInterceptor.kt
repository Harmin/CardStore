package com.exercise.cardstore.core.interceptor

import android.content.Context
import com.exercise.cardstore.core.util.readAssetsFile
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockRequestInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val header = request.header(MOCK)

        if (header != null) {
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(200)
                .body(context.assets.readAssetsFile().toResponseBody(JSON_MEDIA_TYPE))
                .build()
        }

        return chain.proceed(request.newBuilder().removeHeader(MOCK).build())
    }

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaTypeOrNull()
        private const val MOCK = "mock"
    }
}