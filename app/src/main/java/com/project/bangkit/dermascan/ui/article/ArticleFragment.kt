
package com.project.bangkit.dermascan.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.ui.adapter.ArticleAdapter

class ArticleFragment : Fragment(), ArticleAdapter.MyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleList: ArrayList<Article>

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_article)

        articleList = ArrayList()
        articleList.add(Article(R.drawable.artikel1, "Ini dia! Berbagai Penyakit Kulit yang Sering Terjadi di Indonesia", "5 june 2024"))
        articleList.add(Article(R.drawable.artikel2, "8 Mitos dan Fakta Tentang Kulit yang Perlu Kamu Ketahui","15 june 2024"))
        articleList.add(Article(R.drawable.artikel3, "Mudah Banget, Ini 10 Cara Jaga Kesehatan Kulit!","27 Mei 2024"))
        articleList.add(Article(R.drawable.artikel4, "4 Jenis Penyakit Kulit yang Perlu Diwaspadai","12 Februari 2024"))
        articleList.add(Article(R.drawable.artikel5, "Berbagai Jenis Penyakit Kulit yang Tidak Menular","27 Mei 2024"))

        articleAdapter = ArticleAdapter(articleList, this@ArticleFragment)

        recyclerView.adapter = articleAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(position: Int) {
        when(position){
            0 -> startActivity(Intent(activity,Article1Activity::class.java))
            1 -> startActivity(Intent(activity,Article2Activity::class.java))
            2 -> startActivity(Intent(activity,Article3Activity::class.java))
            3 -> startActivity(Intent(activity,Article4Activity::class.java))
            4 -> startActivity(Intent(activity,Article5Activity::class.java))
        }
    }

}