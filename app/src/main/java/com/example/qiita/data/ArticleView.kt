package com.example.qiita.data

import com.example.qiita.constant.ViewType
import java.time.LocalDate

data class ArticleView(
    val article: Article,
    val viewType: ViewType
)

val progressArticleView = ArticleView(
    Article("", "", LocalDate.now(), "", ""),
    ViewType.VIEW_TYPE_PROGRESS
)