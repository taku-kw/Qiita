package com.example.qiita.repository

import com.example.qiita.api.QiitaApi
import com.example.qiita.data.Article
import com.example.qiita.data.ArticleList
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface ArticleListRepository {
    suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): ArticleList
}

class ArticleListRepositoryImpl @Inject constructor(
    private val qiitaApi: QiitaApi
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
}