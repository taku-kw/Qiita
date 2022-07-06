package com.example.qiita.repository

import com.example.qiita.api.QiitaApi
import com.example.qiita.data.Article
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ArticleListRepository {
    private val qiitaApi = QiitaApi()

    suspend fun getArticleList(
        searchWord: String,
        page: Int = 1,
        perPage: Int = 20,
    ): List<Article> {
        val articleList = mutableListOf<Article>()

        qiitaApi.getArticleList(searchWord, page, perPage)?.forEach { data ->
            articleList.add(
                Article(
                    data.user.profile_image_url,
                    data.user.name,
                    LocalDate.parse(data.created_at, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    data.title,
                )
            )
        }

        return articleList
    }
}