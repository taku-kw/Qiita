package com.example.qiita.view.contents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.qiita.R

class ContentsFragment : Fragment() {
    private val url: String
        get() = checkNotNull(arguments?.getString(URL))

    companion object {
        private const val URL = "URL"
        fun newInstance(url: String): ContentsFragment {
            return ContentsFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.contents_webview_fragment, container, false)
        val webView = view.findViewById<View>(R.id.contents_webview) as WebView
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}