package com.example.qiita.repository

import android.content.Context
import com.example.qiita.api.QiitaApi
import com.example.qiita.dao.QiitaDao
import com.example.qiita.data.Article
import com.example.qiita.data.ArticleList
import com.example.qiita.data.entity.ArticleEntity
import com.example.qiita.db.QiitaDb
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

interface ArticleListRepository {
    suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): ArticleList
}

class ArticleListRepositoryImpl @Inject constructor(
    private val qiitaApi: QiitaApi,
    private val qiitaDao: QiitaDao
) : ArticleListRepository {

    override suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): ArticleList {
        val articleList = mutableListOf<Article>()

        val response = qiitaApi.getArticleList(searchWord, page, perPage)

        response.list?.forEach { data ->
            articleList.add(
                Article(
                    data.user.profile_image_url,
                    data.user.name,
                    LocalDate.parse(data.created_at, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    data.title,
                    data.url,
                )
            )
        }

        return ArticleList(articleList, response.totalCount)
    }

    fun getSavedArticleList(): ArticleList {
        val articleList = mutableListOf<Article>()

        val savedArticleList = qiitaDao.getAll()

        savedArticleList.forEach { data ->
            articleList.add(
                Article(
                    data.avatarImagePath,
                    data.userName,
                    data.postDate,
                    data.articleTitle,
                    data.url,
                )
            )
        }

        return ArticleList(articleList, articleList.size)
    }

    fun saveArticleList(articleList: List<Article>) {

        // DBのテーブルを一括削除
        qiitaDao.deleteAll()

        // DBのテーブルに全データ挿入
        val articleEntityList = mutableListOf<ArticleEntity>()
        articleList.forEach { it ->
            articleEntityList.add(
                ArticleEntity(
                    0, // If the field type is Long or Int (or its TypeConverter converts it to a Long or Int), Insert methods treat 0 as not-set while inserting the item.
                    it.avatarImagePath,
                    it.userName,
                    it.postDate,
                    it.articleTitle,
                    it.url,
                )
            )
        }
        qiitaDao.insertAll(articleEntityList)
    }
}