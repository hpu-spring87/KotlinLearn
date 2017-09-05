package me.chunsheng.kotlinlearn.first

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_kotlin_docs.*
import kotlinx.android.synthetic.main.app_bar_kotlin_docs.*
import br.tiagohm.markdownview.css.styles.Github
import kotlinx.android.synthetic.main.content_kotlin_docs.*
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.WindowManager
import me.chunsheng.kotlinlearn.R


class KotlinDocsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mStyle = Github()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_docs)
        setSupportActionBar(toolbar)

        toolbar.title = "Kotlin Docs"
        toolbar.subtitle = "官方文档"

        fab.setOnClickListener { view ->
            Snackbar.make(view, "切换屏幕方向ing", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val orientation = display.orientation
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Configuration.ORIENTATION_LANDSCAPE -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
        markdown_view.addStyleSheet(mStyle);
        //markdown_view.loadMarkdown("**MarkdownView**")
        markdown_view.loadMarkdownFromAsset("basic_syntax/basic_syntax.md")
        //markdown_view.loadMarkdownFromUrl("url")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.kotlin_docs, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        when (item.itemId) {
//            R.id.action_settings -> return true
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.basic_syntax -> {
                markdown_view.loadMarkdownFromAsset("basic_syntax/basic_syntax.md")
            }
            R.id.idioms -> {
                markdown_view.loadMarkdownFromAsset("idioms/idioms.md")
            }
            R.id.coding_conventions -> {
                markdown_view.loadMarkdownFromAsset("coding_conventions/coding_conventions.md")
            }
        //Basics
            R.id.basic_types -> {
                markdown_view.loadMarkdownFromAsset("basic_types/basic_types.md")
            }
            R.id.packages -> {
                markdown_view.loadMarkdownFromAsset("packages/packages.md")
            }
            R.id.control_flow -> {
                markdown_view.loadMarkdownFromAsset("control_flow/control_flow.md")
            }
            R.id.returns_and_jumps -> {
                markdown_view.loadMarkdownFromAsset("returns_and_jumps/returns_and_jumps.md")
            }
        //Classes and Inheritance
            R.id.classes_and_inheritance -> {
                markdown_view.loadMarkdownFromAsset("classes_and_inheritance/classes_and_inheritance.md")
            }
            R.id.properties_and_fields -> {
                markdown_view.loadMarkdownFromAsset("properties_and_fields/properties_and_fields.md")
            }
            R.id.interfaces -> {
                markdown_view.loadMarkdownFromAsset("interfaces/interfaces.md")
            }
            R.id.visibility_modifiers -> {
                markdown_view.loadMarkdownFromAsset("visibility_modifiers/visibility_modifiers.md")
            }
            R.id.extensions -> {
                markdown_view.loadMarkdownFromAsset("extensions/extensions.md")
            }
            R.id.data_classes -> {
                markdown_view.loadMarkdownFromAsset("data_classes/data_classes.md")
            }
            R.id.sealed_classes -> {
                markdown_view.loadMarkdownFromAsset("sealed_classes/sealed_classes.md")
            }
            R.id.generics -> {
                markdown_view.loadMarkdownFromAsset("generics/generics.md")
            }
            R.id.nested_classes -> {
                markdown_view.loadMarkdownFromAsset("nested_classes/nested_classes.md")
            }
            R.id.enum_classes -> {
                markdown_view.loadMarkdownFromAsset("enum_classes/enum_classes.md")
            }
            R.id.object_expressions_and_declarations -> {
                markdown_view.loadMarkdownFromAsset("object_expressions_and_declarations/object_expressions_and_declarations.md")
            }
            R.id.delegation -> {
                markdown_view.loadMarkdownFromAsset("delegation/delegation.md")
            }
            R.id.delegated_properties -> {
                markdown_view.loadMarkdownFromAsset("delegated_properties/delegated_properties.md")
            }
        //Functions and Lambdas
            R.id.functions -> {
                markdown_view.loadMarkdownFromAsset("functions/functions.md")
            }
            R.id.higher_order_functions_and_lambdas -> {
                markdown_view.loadMarkdownFromAsset("higher_order_functions_and_lambdas/higher_order_functions_and_lambdas.md")
            }
            R.id.inline_functions -> {
                markdown_view.loadMarkdownFromAsset("inline_functions/inline_functions.md")
            }
            R.id.coroutines -> {
                markdown_view.loadMarkdownFromAsset("coroutines/coroutines.md")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
