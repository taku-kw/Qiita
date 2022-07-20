package com.example.qiita.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qiita.constant.DbConst.Companion.QIITA_DB_TABLE_NAME
import com.example.qiita.data.entity.ArticleEntity

@Dao
interface QiitaDao {
    @Query("SELECT * FROM $QIITA_DB_TABLE_NAME")
    fun getAll(): List<ArticleEntity>

    @Query("DELETE FROM $QIITA_DB_TABLE_NAME")
    fun deleteAll()

    @Insert
    fun insertAll(articleList: List<ArticleEntity>)
}