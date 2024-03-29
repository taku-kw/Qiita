package com.example.qiita.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiita.R
import com.example.qiita.constant.ArticleListConst
import com.example.qiita.constant.LoadingState
import com.example.qiita.data.Article
import com.example.qiita.view.common.Loading
import com.example.qiita.view.contents.ContentsFragment
import com.example.qiita.viewmodel.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private val model: ArticleListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleListView = view.findViewById<RecyclerView>(R.id.articleListView)
        val adapter = ArticleListAdapter(view.context, mutableListOf())

        articleListView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        articleListView.adapter = adapter

        adapter.setOnArticleClickListener(
            object : ArticleListAdapter.OnArticleClickListener {
                override fun onItemClick(article: Article) {

                    parentFragment!!.parentFragmentManager.beginTransaction()
                        .replace(R.id.topPageFragment, ContentsFragment.newInstance(article.url))
                        .addToBackStack(null)
                        .commit()
                }
            }
        )

        articleListView.apply {
            val linearLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            setAdapter(adapter)
            addOnScrollListener(ArticleListScrollListener(linearLayoutManager, this.adapter as ArticleListAdapter) {
                model.searchNextArticle()
            })
        }

        Loading.setContext(view.context)

        model.articleList.observe(viewLifecycleOwner, Observer { list ->
            val tempAdapter = articleListView.adapter as ArticleListAdapter
            if (list.size <= ArticleListConst.ITEM_PER_PAGE) {
                tempAdapter.setArticleList(list)
            } else {
                tempAdapter.rangeInsertArticleList(
                    list,
                    tempAdapter.itemCount,
                    list.size - tempAdapter.itemCount,
                )
            }
        })

        model.loadingState.observe(viewLifecycleOwner) { state ->
            if (state == LoadingState.LOADING) {
                Loading.show()
            }
            else if (state == LoadingState.NOT_LOADING) {
                Loading.dismiss()
            }
        }

        model.toastMsg.observe(viewLifecycleOwner, Observer { msg ->
            Toast.makeText(view.context, msg, Toast.LENGTH_LONG).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Loading.clearContext()
    }
}