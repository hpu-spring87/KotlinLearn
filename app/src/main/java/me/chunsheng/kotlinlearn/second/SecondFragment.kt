package me.chunsheng.kotlinlearn.second

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.chunsheng.kotlinlearn.R


/**
 */
class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_second, container, false)
        //Find the +1 button
        view.findViewById<Button>(R.id.firstBtn).setOnClickListener {
            activity.startActivity(Intent(activity, KotlinAndroidActivity::class.java))
        }

        return view
    }

}
