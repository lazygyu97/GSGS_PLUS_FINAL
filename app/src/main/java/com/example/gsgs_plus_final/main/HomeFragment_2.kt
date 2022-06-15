package com.example.gsgs_plus_final.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.adapter.PickUpListAdapter
import com.example.gsgs_plus_final.login.PickerJoinActivity
import com.example.gsgs_plus_final.login.PwFindFinishActivity
import com.example.gsgs_plus_final.vo.pick_list

class HomeFragment_2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_home_2, container, false)

        val mainAct = activity as MainActivity
        val page= v.findViewById<LinearLayout>(R.id.page)
        val close_btn=v.findViewById<TextView>(R.id.close_btn)
        //픽업요청서 애니메이션
        val animation_1 = AnimationUtils.loadAnimation(context,R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context,R.anim.translate_down)


        val pickList= arrayListOf<pick_list>(
            pick_list("인천 계양구","서울 강북구"),
            pick_list("인천 서구","서울 강서구"),
            pick_list("인천 남구","서울 마포구")
        )

        val pickAdapter = context?.let { PickUpListAdapter(it,pickList) }
        val pickUp= v.findViewById<ListView>(R.id.using_list_view)
        pickUp.adapter=pickAdapter

        //https://soohyun6879.tistory.com/30 참조해서 더 수정해야함
        pickUp.setOnItemClickListener{parent, view, position, id->
            if (position==0){
                pickUp.visibility=View.INVISIBLE
                mainAct.HideBottomNavi(true)
                 page.startAnimation(animation_1)
                page.visibility=View.VISIBLE

                close_btn.setOnClickListener {
                    pickUp.visibility=View.VISIBLE
                    mainAct.HideBottomNavi(false)
                    page.startAnimation(animation_2)
                    page.visibility=View.INVISIBLE
                }
//                activity?.let {
//                    var intent = Intent(context, PickerJoinActivity::class.java)
//                    startActivity(intent)
//                }
            }
        }
        // Inflate the layout for this fragment
        return v
    }
}