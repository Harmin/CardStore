package com.exercise.cardstore.data.remote

import com.exercise.cardstore.data.remote.dto.CardDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface CardStoreApi {

    @Headers("mock:true")
    @GET("card-list.json")
    suspend fun getCards(): List<CardDto>

    @Headers("mock:true")
    @GET("card/{cardId}.json")
    suspend fun getCard(cardId: String): CardDto

    companion object {
        // could deploy server at your local or use actual url
        const val BASE_URL = "http://localhost:3000/card-list/"
    }
}