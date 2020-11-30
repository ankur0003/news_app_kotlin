package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


   inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    /**
     * you can extend the DIffUTIl or you can make an anonymous like the below line of code
     */
    private val differCallback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    /**it will take the two list and will check for the difference
     *
     */
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
      val view : View =  LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList.get(position)
        /**to reference the viewws directlty
         *
         */
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            setOnClickListener{
                onItemClickListener?.let {it(article) // it refers to onItemCick lambda function defined below and takes article as a parameter

                }
            }
        }
    }

   /**
    *  a click listener for itemView inside a list click onitv and you'll get a web view'
    **/
    private var onItemClickListener: ((Article) -> Unit)?=null
    fun setOnItemClickListener(listener: (Article)->Unit){
        onItemClickListener = listener
    }
}