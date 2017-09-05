package me.chunsheng.kotlinlearn.first

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import br.tiagohm.markdownview.css.styles.Github
import kotlinx.android.synthetic.main.activity_kotlin_code.*
import kotlinx.android.synthetic.main.app_bar_kotlin_code.*
import kotlinx.android.synthetic.main.content_kotlin_code.*
import me.chunsheng.kotlinlearn.R

/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/28
 * Email:weichsh@edaixi.com
 * Function:
 */
class KotlinCodeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val mStyle = Github()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_code)
        setSupportActionBar(codeToolbar)

        codeToolbar.title = "Kotlin Code"
        codeToolbar.subtitle = "官方代码示例"

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Email Us...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val orientation = display.orientation
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Configuration.ORIENTATION_LANDSCAPE -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, code_drawer_layout, codeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        code_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_code_view.setNavigationItemSelectedListener(this)
        //http://stackoverflow.com/questions/6370690/media-queries-how-to-target-desktop-tablet-and-mobile
        mStyle.addMedia("screen and (min-width: 320px)")
        mStyle.addRule("h1", "color: green")
        mStyle.addRule("h2", "color: green")
        mStyle.addRule("h3", "color: green")
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 481px)")
        mStyle.addRule("h1", "color: red")
        mStyle.addRule("h2", "color: red")
        mStyle.addRule("h3", "color: red")
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 641px)")
        mStyle.addRule("h1", "color: blue")
        mStyle.addRule("h2", "color: blue")
        mStyle.addRule("h3", "color: blue")
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 961px)")
        mStyle.addRule("h1", "color: yellow")
        mStyle.addRule("h2", "color: yellow")
        mStyle.addRule("h3", "color: yellow")
        mStyle.endMedia()
        mStyle.addMedia("screen and (min-width: 1025px)")
        mStyle.addRule("h1", "color: gray")
        mStyle.addRule("h2", "color: gray")
        mStyle.addRule("h3", "color: gray")
        mStyle.endMedia()
        mStyle.addMedia("screen and (min-width: 1281px)")
        mStyle.addRule("h1", "color: orange")
        mStyle.addRule("h2", "color: orange")
        mStyle.addRule("h3", "color: orange")
        mStyle.endMedia()
        codeMarkdownview.addStyleSheet(mStyle);
        //markdown_view.loadMarkdown("**MarkdownView**")
        codeMarkdownview.loadMarkdownFromAsset("chapter_01/1.1_ATasteOfKotlin.kt.md")
        //markdown_view.loadMarkdownFromUrl("url")
    }

    override fun onBackPressed() {
        if (code_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            code_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.kotlin_docs, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
        //chapter 01
            R.id.ch_1_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_01/1.1_ATasteOfKotlin.kt.md")
            }
        //chapter 02
            R.id.ch_2_1_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.1.1_HelloWorld.kt.md")
            }
            R.id.ch_2_1_2 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.1.2_Functions.kt.md")
            }
            R.id.ch_2_1_4_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.1.4_1_StringTemplates.kt.md")
            }
            R.id.ch_2_2_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.2.1_2_Properties1.kt.md")
            }
            R.id.ch_2_2_2 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.2.2_CustomAccessors.kt.md")
            }
            R.id.ch_2_3_1_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.3.1_DeclaringEnumClasses.kt.md")
            }
            R.id.ch_2_3_2_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.3.2_1_WhenEnums.kt.md")
            }
            R.id.ch_2_3_5 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.3.5_SmartCasts.kt.md")
            }
            R.id.ch_2_4_2_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.4.2_1_FizzBuzz.kt.md")
            }
            R.id.ch_2_4_3 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.4.3_IteratingOverMaps.kt.md")
            }
            R.id.ch_2_4_4_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.4.4_1_UsingAnInCheck.kt.md")
            }
            R.id.ch_2_5_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_02/2.5.1_TryCatchAndFinally.kt.md")
            }
        //chapter 02
            R.id.ch_3_1_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_03/3.1_1_CollectionsInKotlin.kt.md")
            }
            R.id.ch_3_2_2 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_03/3.2_2_JoinToString.kt.md")
            }
            R.id.ch_3_3_5 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_03/3.3.5_ExtensionProperties.kt.md")
            }
            R.id.ch_3_5_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_03/3.5.1_1_SplittingStrings.kt.md")
            }
            R.id.ch_3_6_1 -> {
                codeMarkdownview.loadMarkdownFromAsset("chapter_03/3.6_1_LocalFunctionsAndExtensions.kt.md")
            }

        }

        code_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}