package com.example.gsgs_plus_final.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.pickUp.BeforePickUpActivity
import com.example.gsgs_plus_final.request.DoingRequestActivity
import net.daum.mf.map.api.MapView

class HomeFragment_1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_home_1, container, false)
        val mainAct = activity as MainActivity

//        val mapView=v.findViewById<ConstraintLayout>(R.id.kakaoMapView)
//        val map = MapView(activity)
//        mapView.addView(map)


        val animation_1 = AnimationUtils.loadAnimation(context,R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context,R.anim.translate_down)

        val page = v.findViewById<LinearLayout>(R.id.page)
        val pick_up_btn =v.findViewById<Button>(R.id.pick_up_btn)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val pl_pick = v.findViewById<Button>(R.id.pl_pick)

        pick_up_btn.setOnClickListener {
            pick_up_btn.visibility=View.INVISIBLE
            mainAct.HideBottomNavi(true)
            page.startAnimation(animation_1)
            page.visibility=View.VISIBLE
        }
        close_btn.setOnClickListener {
            pick_up_btn.visibility=View.VISIBLE

            mainAct.HideBottomNavi(false)
            page.startAnimation(animation_2)
            page.visibility=View.INVISIBLE
        }

        pl_pick.setOnClickListener {
            activity?.let {
                val intent = Intent(context, DoingRequestActivity::class.java)
                startActivity(intent)
            }
        }


        // Inflate the layout for this fragment
        return v



    }
}