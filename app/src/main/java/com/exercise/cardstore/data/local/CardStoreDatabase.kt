package com.exercise.cardstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Card::class],
    version = 1
)
abstract class CardStoreDatabase: RoomDatabase() {

    abstract val dao: CardDao

    companion object {
        const val DATABASE_NAME = "card_store_db"
    }
}