package com.example.qiita.view.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.qiita.R
import com.example.qiita.data.Article
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ArticleListAdapter(private val context: Context, private var articleList: List<Article>) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    private lateinit var clickListener: OnArticleClickListener

    interface OnArticleClickListener {
        fun onItemClick(article: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatarImage.load(Uri.parse(articleList[position].avatarImagePath))
        holder.userName.text = articleList[position].userName
        holder.postDate.text = convStringFromLocalDate(articleList[position].postDate)
        holder.articleTitle.text = articleList[position].articleTitle

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(articleList[position])
        }
    }

    override fun getItemCount(): Int = articleList.size

    private fun getBitmapFromAssets(path : String) : Bitmap {
        val inputStream = context.assets.open(path)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun convStringFromLocalDate(localDate: LocalDate): String {
        val dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
        return localDate.format(dtf)
    }

    fun setArticleList(articleList: List<Article>) {
        this.articleList = articleList
        notifyDataSetChanged()
    }

    fun setOnArticleClickListener(listener: OnArticleClickListener) {
        this.clickListener = listener
    }
}