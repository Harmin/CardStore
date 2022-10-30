package com.exercise.cardstore.data.local

import androidx.room.*

@Dao
interface CardDao {

    @Query("DELETE FROM Card")
    suspend fun clearCards()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(
        cardList: List<Card>
    )

    @Query("SELECT * FROM Card")
    suspend fun getCards(): List<Card>

    @Query("SELECT * FROM Card WHERE id = :id")
    suspend fun getCardById(id: Int): Card?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

}