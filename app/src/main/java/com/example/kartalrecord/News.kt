package com.example.kartalrecord

import androidx.compose.ui.graphics.painter.Painter

data class News(
    val image: Painter,
    val category: String,
    val title: String
)
