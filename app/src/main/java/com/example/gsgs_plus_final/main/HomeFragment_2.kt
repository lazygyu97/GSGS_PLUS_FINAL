package com.example.gsgs_plus_final.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.adapter.PickUpListAdapter
import com.example.gsgs_plus_final.login.PickerJoinActivity
import com.example.gsgs_plus_final.login.PwFindFinishActivity
import com.example.gsgs_plus_final.pickUp.BeforePickUpActivity
import com.example.gsgs_plus_final.vo.pick_list

class HomeFragment_2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home_2, container, false)

        val mainAct = activity as MainActivity
        val page = v.findViewById<LinearLayout>(R.id.page)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val list = v.findViewById<RecyclerView>(R.id.using_list_view)
        val accept =v.findViewById<Button>(R.id.accept)
        // use a linear layout manager
        val mLayoutManager =  LinearLayoutManager(context);
        list.setLayoutManager(mLayoutManager);

        //픽업요청서 애니메이션
        val animation_1 = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context, R.anim.translate_down)
        val pickList = ArrayList<pick_list>()


        pickList.apply {
            add(pick_list("인천 계양구", "서울 강북구"))
            add(pick_list("인천 서구", "서울 강서구"))
            add(pick_list("인천 남구", "서울 마포구"))
        }

        val adapter = PickUpListAdapter(pickList)

        adapter.setOnItemClickListener(object :PickUpListAdapter.OnItemClickListener{
            override fun onItemClick(data: pick_list, pos: Int) {
                list.visibility=View.INVISIBLE
                mainAct.HideBottomNavi(true)
                page.startAnimation(animation_1)
                page.visibility=View.VISIBLE
            }
        })

        close_btn.setOnClickListener {
            list.visibility=View.VISIBLE
            mainAct.HideBottomNavi(false)
            page.startAnimation(animation_2)
            page.visibility=View.INVISIBLE
        }

        accept.setOnClickListener {
            activity?.let{
                val intent = Intent(context, BeforePickUpActivity::class.java)
                startActivity(intent)
            }

        }

        list.adapter = adapter


        // Inflate the layout for this fragment
        return v
    }
}