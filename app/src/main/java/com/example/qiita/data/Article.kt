package com.example.qiita.data

import java.time.LocalDate

data class Article (
    val avatarImagePath: String,
    val userName: String,
    val postDate: LocalDate,
    val articleTitle: String,
)