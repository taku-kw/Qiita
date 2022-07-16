package com.example.qiita.data.apiResponse.getArticleList

data class GetArticleListResponseWithTotalCount(
    val list: List<GetArticleListResponse>?,
    val totalCount: Int
)