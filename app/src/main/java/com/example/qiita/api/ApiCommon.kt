package com.example.qiita.api

import retrofit2.Call
import retrofit2.Response
import kotlin.Exception

fun <T> apiCall(api: Call<T>): Response<T> {
    try {
        val response = api.execute()
        if (!response.isSuccessful) {
            throw Exception("HTTP Status Error : ${response.code()}")
        }
        return response
    } catch (e: Exception) {
        throw e
    }
}