package com.example.qiita.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita.constant.ArticleListConst
import com.example.qiita.constant.LoadingState
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
    val loadingState = MutableLiveData(LoadingState.NOT_LOADING)
    val toastMsg = MutableLiveData<String>()

    private var searchWord = ""
    private var page = 0
    private val itemPerPage = ArticleListConst.ITEM_PER_PAGE
    private var totalCount = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = articleListRepository.getSavedArticleList()
            articleList.postValue(data.list.toMutableList())
            // 無限スクロールの抑制
            this@ArticleListViewModel.page = 1
            this@ArticleListViewModel.totalCount = data.list.size
        }
    }

    fun searchArticle(
        searchWord: String,
        page: Int = 1,
        existingArticleList: MutableList<Article> = mutableListOf()
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                beginLoading()
                // TODO ローディングの確認用にsleep
                Thread.sleep(3000)

                val data = articleListRepository.getArticleList(searchWord, page, itemPerPage)

                val newArticleList = data.list as MutableList<Article>
                val joinedArticleList = (existingArticleList + newArticleList).toMutableList()
                articleList.postValue(joinedArticleList)
                endLoading()

                this@ArticleListViewModel.searchWord = searchWord
                this@ArticleListViewModel.page = page
                this@ArticleListViewModel.totalCount = data.totalCount

                // 初回読み込みの場合、DBに保存する
                if (page == 1) {
                    articleListRepository.saveArticleList(joinedArticleList)
                }

            } catch (e: Exception) {
                Log.w("searchArticle", e.toString())
                toastMsg.postValue(e.toString())
            }
        }
    }

    fun searchNextArticle() {
        if (page * itemPerPage < totalCount) {
            searchArticle(searchWord, ++page, articleList.value!!)
        }
    }

    fun reset() {
        articleList.postValue(mutableListOf())
    }

    private fun beginLoading() {
        loadingState.postValue(LoadingState.LOADING)
    }

    private fun endLoading() {
        loadingState.postValue(LoadingState.NOT_LOADING)
    }
}