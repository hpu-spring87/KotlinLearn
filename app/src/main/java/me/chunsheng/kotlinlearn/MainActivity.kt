package me.chunsheng.kotlinlearn

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.chunsheng.kotlinlearn.first.FirstFragment
import me.chunsheng.kotlinlearn.second.SecondFragment
import me.chunsheng.kotlinlearn.third.ThirdFragment

class MainActivity : AppCompatActivity() {

    init {

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewpager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewpager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewpager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var adapter = KotlinPagerAdapter(supportFragmentManager)
        viewpager.setAdapter(adapter)
    }


    class KotlinPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            if (position == 0) {
                return FirstFragment()
            } else if (position == 1) {
                return SecondFragment()
            } else {
                return ThirdFragment()
            }
        }

        override fun getCount(): Int {
            return 3
        }
    }

}
