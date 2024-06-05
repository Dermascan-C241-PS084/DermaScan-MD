package com.project.bangkit.dermascan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.ui.article.Article

class ArticleAdapter (val artikelList:ArrayList<Article>, val listener: MyClickListener):RecyclerView.Adapter<ArticleAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imageView: ImageView = itemView.findViewById(R.id.img_article)
        val textView: TextView = itemView.findViewById(R.id.tv_item_title)
        val tanggalView: TextView = itemView.findViewById(R.id.tv_item_published_date)

        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                listener.onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val artikel = artikelList[position]
        holder.textView.text = artikel.judul
        holder.tanggalView.text = artikel.tanggal
        holder.imageView.setImageResource(artikel.image)
    }

    override fun getItemCount(): Int {
        return artikelList.size
    }

    interface MyClickListener{
        fun onClick(position: Int)
    }
}