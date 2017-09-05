package me.chunsheng.kotlinlearn.second

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import me.chunsheng.kotlinlearn.R
import me.chunsheng.kotlinlearn.second.model.DailyData

/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/24
 * Email:weichsh@edaixi.com
 * Function:
 */
class DetailActivity : AppCompatActivity() {

    companion object {
        val DailyData = "DetailActivity:DailyData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val forecast = intent.getParcelableExtra<DailyData>(DailyData)
        Picasso.with(this).load(forecast.picture2).into(image)
        Picasso.with(this).load(forecast.fenxiang_img).into(longImage)
        dateline.text = forecast.dateline
        caption.text = forecast.caption
        content.text = forecast.content
        note.text = forecast.note
        translation.text = forecast.translation

        share.setOnClickListener {
            Toast.makeText(this, "假装分享成功...", Toast.LENGTH_SHORT).show()
        }

        toolbar.setNavigationOnClickListener({ onBackPressed() })
    }

}