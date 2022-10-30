package com.exercise.cardstore.di

import android.content.Context
import com.exercise.cardstore.core.interceptor.MockRequestInterceptor
import com.exercise.cardstore.data.remote.CardStoreApi
import com.exercise.cardstore.data.remote.Constants.Api.CONNECT_TIMEOUT_IN_S
import com.exercise.cardstore.data.remote.Constants.Api.READ_TIMEOUT_IN_S
import com.exercise.cardstore.data.remote.Constants.Api.WRITE_TIMEOUT_IN_S
import com.exercise.cardstore.data.remote.adapter.ColorAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context) = OkHttpClient()
        .newBuilder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(MockRequestInterceptor(context))
        .connectTimeout(CONNECT_TIMEOUT_IN_S, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_IN_S, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_IN_S, TimeUnit.SECONDS)
        .build()


    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(ColorAdapter())
        .build()

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient, moshi: Moshi): CardStoreApi = Retrofit.Builder()
        .baseUrl(CardStoreApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
        .create(CardStoreApi::class.java)

}
