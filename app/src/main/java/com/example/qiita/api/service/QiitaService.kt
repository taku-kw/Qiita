package com.example.qiita.api.service

import com.example.qiita.data.apiResponse.getArticleList.GetArticleListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaService {

    @GET("/api/v2/items")
    fun getArticleList(
        @Query("query") searchWord: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Call<List<GetArticleListResponse>>
}