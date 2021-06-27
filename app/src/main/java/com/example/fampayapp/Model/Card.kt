package com.example.fampayapp.Model

data class Card (
    val bg_color: String,
    val bg_image: BgImage,
    val cta: List<Cta>,
    val description: String,
    val formatted_description: FormattedDescription,
    val formatted_title: FormattedTitle,
    val icon: Icon,
    val is_disabled: Boolean,
    val name: String,
    val title: String,
    val url: String
)