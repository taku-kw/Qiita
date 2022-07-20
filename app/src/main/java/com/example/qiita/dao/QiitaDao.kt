package com.example.qiita.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qiita.data.entity.ArticleEntity

@Dao
interface QiitaDao {
    @Query("SELECT * FROM article_table")
    fun getAll(): List<ArticleEntity>

    @Query("DELETE FROM article_table")
    fun deleteAll()

    @Insert
    fun insertAll(articleList: List<ArticleEntity>)
}