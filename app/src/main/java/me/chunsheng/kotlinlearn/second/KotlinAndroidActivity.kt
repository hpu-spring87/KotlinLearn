package me.chunsheng.kotlinlearn.second

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_kotlin_android.*
import me.chunsheng.kotlinlearn.R
import me.chunsheng.kotlinlearn.second.model.DailyData
import me.chunsheng.kotlinlearn.second.adapters.ListAdapter
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/18
 * Email:weichsh@edaixi.com
 * Function:
 */
class KotlinAndroidActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_android)

        toolbar1.setNavigationOnClickListener({ onBackPressed() })
    }


    override fun onResume() {
        super.onResume()
        Thread({
            val format = SimpleDateFormat("yyyy-MM-dd")
            val calendar = Calendar.getInstance()
            val gson = Gson()
            val array = ArrayList<DailyData>()

            for (i in 0..10) {
                calendar.add(Calendar.DATE, -i)
                val urlJsonStr = URL("http://open.iciba.com/dsapi/?date=" + format.format(calendar.time)).readText()
                val daiyData = gson.fromJson(urlJsonStr, DailyData::class.java)
                array.add(daiyData)
                calendar.add(Calendar.DATE, i)
            }

            val adapter = ListAdapter(array) {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DailyData, it)
                startActivity(intent)
            }
            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.VERTICAL
            runOnUiThread({
                testUrl.text = array[0].note
                testList.layoutManager = llm
                testList.adapter = adapter
            })
            /**
             * {
            "sid": "2698",
            "tts": "http://news.iciba.com/admin/tts/2017-08-23-day",
            "content": "Complaining is like vomiting. Afterwards you feel better but everyone around you feels sick.",
            "note": "抱怨就跟呕吐一样。你自己是爽了，可你周围的人都得跟着遭殃。",
            "love": "1724",
            "translation": "词霸小编：不要再抱怨这个社会有多么虚伪和不公，因为我们都是帮凶。",
            "picture": "http://cdn.iciba.com/news/word/20170823.jpg",
            "picture2": "http://cdn.iciba.com/news/word/big_20170823b.jpg",
            "caption": "词霸每日一句",
            "dateline": "2017-08-23",
            "s_pv": "0",
            "sp_pv": "0",
            "tags": [
            {
            "id": null,
            "name": null
            }
            ],
            "fenxiang_img": "http://cdn.iciba.com/web/news/longweibo/imag/2017-08-23.jpg"
            }
             */
        }).start()

    }

}