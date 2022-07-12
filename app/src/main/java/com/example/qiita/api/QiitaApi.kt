package com.example.qiita.api

import com.example.qiita.api.service.QiitaService
import com.example.qiita.data.apiResponse.getArticleList.GetArticleListResponseWithTotalCount
import javax.inject.Inject

interface QiitaApi {
    abstract suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): GetArticleListResponseWithTotalCount
}

class QiitaApiImpl @Inject constructor(
    private val service: QiitaService
) : QiitaApi {

    override suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): GetArticleListResponseWithTotalCount {
        val response = apiCall(service.getArticleList(searchWord, page, perPage))
        val totalCount = response.headers()["Total-Count"] ?: "1"
        return GetArticleListResponseWithTotalCount(
            response.body(),
            totalCount.toInt()
        )
    }
}

