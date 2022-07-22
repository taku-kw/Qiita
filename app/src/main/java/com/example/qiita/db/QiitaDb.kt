package com.example.qiita.db

import androidx.room.*
import com.example.qiita.dao.QiitaDao
import com.example.qiita.data.entity.ArticleEntity
import com.example.qiita.data.entity.type_converter.LocalDateConverter

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class QiitaDb : RoomDatabase() {

    abstract fun qiitaDao(): QiitaDao

}