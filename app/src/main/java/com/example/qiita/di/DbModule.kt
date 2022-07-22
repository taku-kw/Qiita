package com.example.qiita.di

import android.content.Context
import androidx.room.Room
import com.example.qiita.constant.DbConst.Companion.QIITA_DB_NAME
import com.example.qiita.dao.QiitaDao
import com.example.qiita.db.QiitaDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideQiitaDb(
        @ApplicationContext context: Context
    ): QiitaDb {
        return Room.databaseBuilder(
            context,
            QiitaDb::class.java,
            QIITA_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: QiitaDb): QiitaDao {
        return db.qiitaDao()
    }
}