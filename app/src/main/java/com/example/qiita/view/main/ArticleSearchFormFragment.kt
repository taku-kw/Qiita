package com.example.qiita.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.qiita.R
import com.example.qiita.viewmodel.ArticleListViewModel

class ArticleSearchFormFragment: Fragment() {
    private val model: ArticleListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.article_search_form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchWord = view.findViewById<EditText>(R.id.inputArea)
        val searchButton = view.findViewById<Button>(R.id.searchButton)

        searchButton.setOnClickListener{
            model.searchArticle(searchWord.text.toString())
        }
    }
}
