package me.chunsheng.kotlinlearn.first

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.chunsheng.kotlinlearn.R


/**
 */
class FirstFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_first, container, false)
        //Find the +1 button
        view.findViewById(R.id.firstBtn).setOnClickListener {
            activity.startActivity(Intent(activity, FirstActivity::class.java))
        }
        return view
    }

}
