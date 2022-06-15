package com.example.gsgs_plus_final.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gsgs_plus_final.R


class UseListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =inflater.inflate(R.layout.fragment_use_list, container, false)

        val mainAct = activity as MainActivity
        mainAct.changeTop(false)

        val ing_btn = v.findViewById<Button>(R.id.ing_button)
        val ed_btn = v.findViewById<Button>(R.id.ed_button)

        ing_btn.setOnClickListener {
            ing_btn.setBackgroundResource(R.drawable.button_shape_2)
            ed_btn.setBackgroundResource(R.drawable.button_shape)
        }
        ed_btn.setOnClickListener {
            ing_btn.setBackgroundResource(R.drawable.button_shape)
            ed_btn.setBackgroundResource(R.drawable.button_shape_2)
        }

        // Inflate the layout for this fragment
        return v
    }


}