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

class QiitaApi {
    private val service: QiitaService by lazy {
        // Retrofitに設定する、OkHttpオブジェクト
        // ロギングの設定
        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        // リクエストヘッダの設定
        val httpRequestHeader = Interceptor { chain ->
            val original = chain.request()

            // headerを設定
            val request = original.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.QIITA_API_KEY}")
                .build()

            return@Interceptor chain.proceed(request)
        }
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLogging)
            addInterceptor(httpRequestHeader)
        }.build()

        // Retrofitに設定する、JSONパースを行うオブジェクト
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        // Retrofitのオブジェクト
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://qiita.com")
            addConverterFactory(GsonConverterFactory.create(gson))
            client(httpClient)
        }.build()

        retrofit.create(QiitaService::class.java)
    }

    suspend fun getArticleList(
        searchWord: String,
        page: Int = 1,
        perPage: Int = 20,
    ): List<GetArticleListResponse>? {
        val api = service.getArticleList(searchWord, page, perPage)
        val responseBody = apiCall(api)
        return responseBody.body()
    }
}

