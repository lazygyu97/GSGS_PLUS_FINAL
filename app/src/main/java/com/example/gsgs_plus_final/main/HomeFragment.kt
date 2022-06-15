package com.example.gsgs_plus_final.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.login.PickerJoinActivity
import net.daum.mf.map.api.MapView


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)
        childFragmentManager.beginTransaction().add(R.id.home_layout, HomeFragment_1(), "home")
            .commit()


        val pl_btn = v.findViewById<LinearLayout>(R.id.pl_button)
        val do_btn = v.findViewById<LinearLayout>(R.id.do_button)

        fun ask_picker() {
            val ask_pick = AlertDialog.Builder(context)
            ask_pick
                .setMessage("아직 배송회원이 아니시군요?\n가입하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        activity?.let{
                            val intent = Intent(context, PickerJoinActivity::class.java)
                            startActivity(intent)
                        }

                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->
                        childFragmentManager.beginTransaction()
                            .replace(R.id.home_layout, HomeFragment_1()).commit()
                        pl_btn.setBackgroundResource(R.drawable.button_shape_2)
                        do_btn.setBackgroundResource(R.drawable.button_shape)
                    })
            // 다이얼로그를 띄워주기
            ask_pick.show()
        }
        pl_btn.setOnClickListener {
            val res = childFragmentManager.findFragmentById(R.id.home_layout).toString()
            Log.d("지금", res)
            val str = res.chunked(14)
            if (str[0] == "HomeFragment_2") {
                childFragmentManager.beginTransaction().replace(R.id.home_layout, HomeFragment_1())
                    .commit()
            } else {
                false
            }
            pl_btn.setBackgroundResource(R.drawable.button_shape_2)
            do_btn.setBackgroundResource(R.drawable.button_shape)


        }
        do_btn.setOnClickListener {
            ask_picker()
            pl_btn.setBackgroundResource(R.drawable.button_shape)
            do_btn.setBackgroundResource(R.drawable.button_shape_2)
            childFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeFragment_2()).commit();
        }

        // Inflate the layout for this fragment
        return v


    }

}