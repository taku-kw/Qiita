package com.example.qiita.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "article_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "avatar_image_path")
    val avatarImagePath: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "post_date")
    val postDate: LocalDate,
    @ColumnInfo(name = "article_title")
    val articleTitle: String,
    val url: String,
    )