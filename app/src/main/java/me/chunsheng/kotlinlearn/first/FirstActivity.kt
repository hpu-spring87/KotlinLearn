package me.chunsheng.kotlinlearn.first

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_first.*
import me.chunsheng.kotlinlearn.R
import android.support.v4.content.ContextCompat
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView


/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/28
 * Email:weichsh@edaixi.com
 * Function:首页模块
 */
class FirstActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        docsBtn.setOnClickListener({ startActivity(Intent(this, KotlinDocsActivity::class.java)) })
        codeBtn.setOnClickListener({ startActivity(Intent(this, KotlinCodeActivity::class.java)) })
        if (bgAnimationTop is ImageView) {
            val avd = ContextCompat.getDrawable(
                    this, R.drawable.first_bg_animation) as AnimatedVectorDrawable
            (bgAnimationTop as ImageView).setImageDrawable(avd)
            avd.start()
        }

        if (bgAnimationCenter is ImageView) {
            val avd = ContextCompat.getDrawable(
                    this, R.drawable.first_bg_animation) as AnimatedVectorDrawable
            (bgAnimationCenter as ImageView).setImageDrawable(avd)
            avd.start()
        }

        if (bgAnimationBottom is ImageView) {
            val avd = ContextCompat.getDrawable(
                    this, R.drawable.first_bg_animation) as AnimatedVectorDrawable
            (bgAnimationBottom as ImageView).setImageDrawable(avd)
            avd.start()
        }
    }

}