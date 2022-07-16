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
import com.example.qiita.data.sample.articleSampleData
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
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.article_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleListView = view.findViewById<RecyclerView>(R.id.articleListView)
        val adapter = ArticleListAdapter(view.context, articleSampleData)

        articleListView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        articleListView.adapter = adapter

        model.articleList.observe(viewLifecycleOwner, Observer { list ->
            val tempAdapter = articleListView.adapter as ArticleListAdapter
            tempAdapter.setArticleList(list)
            tempAdapter.notifyDataSetChanged()
        })

        model.toastMsg.observe(viewLifecycleOwner, Observer { msg ->
            Toast.makeText(view.context, msg, Toast.LENGTH_LONG).show()
        })
    }
}