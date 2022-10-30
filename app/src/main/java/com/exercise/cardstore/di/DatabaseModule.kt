package com.exercise.cardstore.di

import android.content.Context
import androidx.room.Room
import com.exercise.cardstore.data.local.CardStoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStoreDatabase(@ApplicationContext context: Context): CardStoreDatabase {
        return Room.databaseBuilder(
            context,
            CardStoreDatabase::class.java,
            CardStoreDatabase.DATABASE_NAME
        ).build()
    }
}