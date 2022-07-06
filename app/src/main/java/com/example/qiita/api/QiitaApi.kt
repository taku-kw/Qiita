package com.example.qiita.api

import com.example.qiita.api.service.QiitaService
import com.example.qiita.data.apiResponse.getArticleList.GetArticleListResponse
import retrofit2.Retrofit

class QiitaApi {
    private val service: QiitaService by lazy {
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://qiita.com")
        }.build()

        retrofit.create(QiitaService::class.java)
    }

    suspend fun getArticleList(
        searchWord: String,
        page: Int = 1,
        perPage: Int = 20,
    ): List<GetArticleListResponse>? {
        val api = service.getArticleList(page, perPage, searchWord)
        val responseBody = api.execute()
        return responseBody.body()
    }

}

