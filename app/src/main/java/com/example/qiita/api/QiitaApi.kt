package com.example.qiita.api

import com.example.qiita.BuildConfig
import com.example.qiita.api.service.QiitaService
import com.example.qiita.data.apiResponse.getArticleList.GetArticleListResponse
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

interface QiitaApi {
    abstract suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): List<GetArticleListResponse>?
}

class QiitaApiImpl @Inject constructor(
    private val service: QiitaService
) : QiitaApi {

    override suspend fun getArticleList(
        searchWord: String,
        page: Int,
        perPage: Int,
    ): List<GetArticleListResponse>? {
        val api = service.getArticleList(searchWord, page, perPage)
        return apiCall(api)
    }
}

