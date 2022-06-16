package com.example.gsgs_plus_final.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.FragmentTransaction
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.databinding.FragmentHome1Binding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

        val pick_up_btn =v.findViewById<Button>(R.id.pick_up_btn)
        val page = v.findViewById<LinearLayout>(R.id.page)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)

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

        // Inflate the layout for this fragment
        return v



    }
}
//     다른 액티비티 및 프래그먼트에서 바인딩을 사용하지 않았기 때문에 통일함
//    // 바인딩 객체 타입에 ?를 붙여서 null을 허용 해줘야한다. ( onDestroy 될 때 완벽하게 제거를 하기위해 )
//    private var mBinding: FragmentHome1Binding? = null
//    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
//    private val binding get() = mBinding!!

//        mBinding= FragmentHome1Binding.inflate(inflater,container,false)
//        val mainAct = activity as MainActivity
//
//
//        //test할 때 밑에 세줄 주석
////        val map=MapView(activity)
////        val mapView=binding.kakaoMapView
////        mapView.addView(map)
//
//
//        //픽업요청서 애니메이션
//        val animation_1 = AnimationUtils.loadAnimation(context,R.anim.translate_up)
//        val animation_2 = AnimationUtils.loadAnimation(context,R.anim.translate_down)
//
//        val pick_up_btn = binding.pickUpBtn
//        val page = binding.page
//        val close_btn = binding.closeBtn
//
//        pick_up_btn.setOnClickListener {
//
//            val currentUser = Firebase.auth.currentUser
//            pick_up_btn.visibility=View.INVISIBLE
//            mainAct.HideBottomNavi(true)
//            page.startAnimation(animation_1)
//            page.visibility=View.VISIBLE
//        }
//        close_btn.setOnClickListener {
//            pick_up_btn.visibility=View.VISIBLE
//
//            mainAct.HideBottomNavi(false)
//            page.startAnimation(animation_2)
//            page.visibility=View.INVISIBLE
//        }
//        return binding.root