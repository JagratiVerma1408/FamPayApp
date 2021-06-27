package com.example.fampayapp.Model

data class CardGroup(
    val card_type: Int,
    val cards: List<Card>,
    val design_type: String,
    val height: Int,
    val id: Int,
    val is_scrollable: Boolean,
    val name: String
)