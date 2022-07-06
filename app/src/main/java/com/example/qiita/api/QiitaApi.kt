package com.example.qiita.api

import com.example.qiita.api.service.QiitaService
import com.example.qiita.data.apiResponse.getArticleList.GetArticleListResponse
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QiitaApi {
    private val service: QiitaService by lazy {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://qiita.com")
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()

        retrofit.create(QiitaService::class.java)
    }

    suspend fun getArticleList(
        searchWord: String,
        page: Int = 1,
        perPage: Int = 20,
    ): List<GetArticleListResponse>? {
        val api = service.getArticleList(searchWord, page, perPage)
        val responseBody = api.execute()
        return responseBody.body()
    }

}

