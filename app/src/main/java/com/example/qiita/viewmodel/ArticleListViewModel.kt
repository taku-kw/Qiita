package com.example.qiita.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita.data.Article
import com.example.qiita.repository.ArticleListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleListViewModel : ViewModel() {
    val articleList = MutableLiveData<MutableList<Article>>(mutableListOf())
    private val articleListRepository = ArticleListRepository()

    fun searchArticle(searchWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newArticleList = articleListRepository.getArticleList(searchWord) as MutableList<Article>
            articleList.postValue(newArticleList)
        }
    }
}