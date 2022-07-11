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

    fun searchArticle(searchWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newArticleList =
                    articleListRepository.getArticleList(searchWord, 1, 20) as MutableList<Article>
                articleList.postValue(newArticleList)
            } catch (e: Exception) {
                Log.w("searchArticle", e.toString())
                toastMsg.postValue(e.toString())
            }
        }
    }
}