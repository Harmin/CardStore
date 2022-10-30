package com.exercise.cardstore.data.local

class CardDaoFake : CardDao{
    private var cardList = mutableListOf<Card>()

    override suspend fun clearCards() {
        cardList.clear()
    }

    override suspend fun insertCards(cardList: List<Card>) {
        this.cardList.clear()
        this.cardList.addAll(cardList)
    }

    override suspend fun getCards(): List<Card> {
       return cardList
    }

    override suspend fun getCardById(id: Int): Card? {
        return cardList.find { it.id == id }
    }

    override suspend fun insertCard(card: Card) {
        cardList.add(card)
    }

    override suspend fun deleteCard(card: Card) {
        cardList.remove(card)
    }
}