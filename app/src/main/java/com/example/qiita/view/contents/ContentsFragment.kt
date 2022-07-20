package com.example.qiita.view.contents

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.qiita.R
import com.example.qiita.constant.UrlConst.Companion.QIITA_HOST_URL

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
        return inflater.inflate(R.layout.contents_webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = view.findViewById<View>(R.id.contents_webview) as WebView
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return if (request?.url?.host == QIITA_HOST_URL) {
                    // WebViewで表示
                    false
                } else {
                    // 外部ブラウザで表示
                    view?.context?.startActivity(Intent(Intent.ACTION_VIEW, request?.url))
                    true
                }
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}