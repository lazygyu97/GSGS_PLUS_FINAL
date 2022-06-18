package com.example.gsgs_plus_final.main

import android.content.Intent
import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment_2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home_2, container, false)

        val mainAct = activity as MainActivity
        val page = v.findViewById<LinearLayout>(R.id.page)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val list = v.findViewById<RecyclerView>(R.id.using_list_view)
        val accept = v.findViewById<Button>(R.id.accept)
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(context);
        list.setLayoutManager(mLayoutManager);

        //DB접근을 위한 Firebase 객체 선언 부분

        val db = Firebase.firestore
        val docRef = db.collection("pick_up_request")


        //픽업요청서 애니메이션
        val animation_1 = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context, R.anim.translate_down)
        val pickList = ArrayList<pick_list>()



        //픽업요청서 띄우기(조건 플레그 존재)
        docRef.whereEqualTo("pick_up_check_flag", "0").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //픽업 요청서 전부 다 넘어오는지 확인 Lod
                    Log.d("pick_up_list:", document.data.toString())
                    // Log.d("documentId:",document.id)
                    Log.d("addrStart:", document.data["pick_up_item_addr_start"].toString())
                    Log.d("addrEnd:", document.data["pick_up_item_addr_end"].toString())


                    val start_addr: String = document.data["pick_up_item_addr_start"].toString()
                    val end_addr: String = document.data["pick_up_item_addr_end"].toString()
                    val request_cost: String = document.data["pick_up_item_cost"].toString()
                    val document_id: String = document.id




                    pickList.apply {

                        add(pick_list(start_addr, end_addr, request_cost, document_id))

                    }

                }

                val adapter = PickUpListAdapter(pickList)
                var accept_doc_id: String = "No id"

                adapter.setOnItemClickListener(object : PickUpListAdapter.OnItemClickListener {
                    override fun onItemClick(data: pick_list, pos: Int) {


                        list.visibility = View.INVISIBLE
                        mainAct.HideBottomNavi(true)
                        page.startAnimation(animation_1)
                        page.visibility = View.VISIBLE

                        v.findViewById<TextView>(R.id.request_addr_start).text = data.addr_start
                        v.findViewById<TextView>(R.id.request_addr_end).text = data.addr_end
                        v.findViewById<TextView>(R.id.request_cost).text = data.request_cost
                        accept_doc_id = data.document_id

                        Log.d("accept_doc_id", accept_doc_id)

                        docRef.document(accept_doc_id).get().addOnSuccessListener { task ->
                            if (task.data!!.get("pick_up_check_flag") == "1") {

                                Toast.makeText(context,
                                    "이미 배차된 배송입니다!\n새로고침 해주세요!", Toast.LENGTH_LONG).show()
                                list.visibility = View.VISIBLE
                                mainAct.HideBottomNavi(false)
                                page.startAnimation(animation_2)
                                page.visibility = View.INVISIBLE
                                return@addOnSuccessListener


                            }
                        }
                    }
                })

                close_btn.setOnClickListener {
                    list.visibility = View.VISIBLE
                    mainAct.HideBottomNavi(false)
                    page.startAnimation(animation_2)
                    page.visibility = View.INVISIBLE
                }

                accept.setOnClickListener {
                    activity?.let {


                        docRef.document(accept_doc_id).update("pick_up_check_flag", "1")

                        val intent = Intent(context, BeforePickUpActivity::class.java)
                        startActivity(intent)
                    }

                }

                list.adapter = adapter


                // Inflate the layout for this fragment


            }.addOnFailureListener { exception ->
                Log.w("No Such document!!!", exception)
            }




        return v

    }
}