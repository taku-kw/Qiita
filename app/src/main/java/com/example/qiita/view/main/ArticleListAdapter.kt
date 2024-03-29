package com.example.qiita.view.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.qiita.R
import com.example.qiita.constant.ViewType
import com.example.qiita.data.Article
import com.example.qiita.data.ArticleView
import com.example.qiita.data.progressArticleView
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ArticleListAdapter(private val context: Context, private var articleList: MutableList<ArticleView>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarImage : CircleImageView
        val userName : TextView
        val postDate : TextView
        val articleTitle: TextView

        init {
            avatarImage = view.findViewById(R.id.avatarImage)
            userName = view.findViewById(R.id.userName)
            postDate = view.findViewById(R.id.postDate)
            articleTitle = view.findViewById(R.id.articleTitle)
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    private lateinit var clickListener: OnArticleClickListener

    interface OnArticleClickListener {
        fun onItemClick(article: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ViewType.VIEW_TYPE_DATA.ordinal) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
            DataViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.infinite_loading, parent, false)
            ProgressViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            val article = articleList[position].article
            holder.avatarImage.load(Uri.parse(article.avatarImagePath))
            holder.userName.text = article.userName
            holder.postDate.text = convStringFromLocalDate(article.postDate)
            holder.articleTitle.text = article.articleTitle

            holder.itemView.setOnClickListener {
                clickListener.onItemClick(article)
            }
        }
    }

    override fun getItemCount(): Int = articleList.size

    override fun getItemViewType(position: Int): Int {
        return articleList[position].viewType.ordinal
    }

    private fun getBitmapFromAssets(path : String) : Bitmap {
        val inputStream = context.assets.open(path)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun convStringFromLocalDate(localDate: LocalDate): String {
        val dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
        return localDate.format(dtf)
    }

    fun setArticleList(articleList: List<Article>) {
        this.articleList = convArticleViewList(articleList)
        notifyDataSetChanged()
    }

    fun rangeInsertArticleList(articleList: List<Article>, positionStart: Int, itemCount: Int) {
        this.articleList = convArticleViewList(articleList)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun setOnArticleClickListener(listener: OnArticleClickListener) {
        this.clickListener = listener
    }

    fun addProgressView() {
        this.articleList.add(progressArticleView)
        notifyItemInserted(this.articleList.size - 1)
    }

    fun removeProgressView() {
        this.articleList.removeLast()
        notifyItemRemoved(this.articleList.size)
    }

    private fun convArticleViewList(articleList: List<Article>): MutableList<ArticleView> {
        val articleViewList = mutableListOf<ArticleView>()

        articleList.forEach { article ->
            articleViewList.add(
                ArticleView(article, ViewType.VIEW_TYPE_DATA)
            )
        }

        return articleViewList
    }
}