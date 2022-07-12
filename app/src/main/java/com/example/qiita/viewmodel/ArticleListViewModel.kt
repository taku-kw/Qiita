package com.example.qiita.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita.data.Article
import com.example.qiita.repository.ArticleListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleListRepository: ArticleListRepository
) : ViewModel() {
    val articleList = MutableLiveData<MutableList<Article>>(mutableListOf())
    val toastMsg = MutableLiveData<String>()

    private var searchWord = ""
    private var page = 0
    private val perPage = 20
    private var totalCount = 0
    var isNewSearch = true

    fun searchArticle(
        searchWord: String,
        page: Int = 1,
        existingArticleList: MutableList<Article> = mutableListOf()
    ) {
        if (existingArticleList.isEmpty()) {
            isNewSearch = true
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = articleListRepository.getArticleList(searchWord, page, perPage)

                val newArticleList = data.list as MutableList<Article>
                articleList.postValue((existingArticleList + newArticleList).toMutableList())

                this@ArticleListViewModel.searchWord = searchWord
                this@ArticleListViewModel.page = page
                this@ArticleListViewModel.totalCount = data.totalCount
            } catch (e: Exception) {
                Log.w("searchArticle", e.toString())
                toastMsg.postValue(e.toString())
            }
        }
    }

    fun searchNextArticle() {
        if (page * perPage <= totalCount) {
            isNewSearch = false
            searchArticle(searchWord, ++page, articleList.value!!)
        }
    }
}