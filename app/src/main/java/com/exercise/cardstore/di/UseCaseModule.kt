package com.exercise.cardstore.di

import com.exercise.cardstore.domain.repository.CardRepository
import com.exercise.cardstore.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCardUseCases(repository: CardRepository): CardUseCases {
        return CardUseCases(
            getCards = GetCards(repository),
            deleteCard = DeleteCard(repository),
            addCard = AddCard(repository),
            getCard = GetCard(repository)
        )
    }
}