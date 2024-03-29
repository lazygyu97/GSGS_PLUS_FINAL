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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.adapter.PickUpListAdapter
import com.example.gsgs_plus_final.login.PickerJoinActivity
import com.example.gsgs_plus_final.login.PwFindFinishActivity
import com.example.gsgs_plus_final.pickUp.BeforePickUpActivity
import com.example.gsgs_plus_final.vo.pick_list
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapView

class HomeFragment_2 : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var tmapView: TMapView? = null
    var tmap: TMapGpsManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home_2, container, false)
        val mainAct = activity as MainActivity

        val maps = v.findViewById<ConstraintLayout>(R.id.TMapView)
        tmapView = TMapView(context)
        tmapView!!.setSKTMapApiKey("l7xx961891362ed44d06a261997b67e5ace6")


        tmapView!!.setZoom(17f)
        tmapView!!.setIconVisibility(true)
        tmapView!!.setMapType(TMapView.MAPTYPE_STANDARD)
        tmapView!!.setLanguage(TMapView.LANGUAGE_KOREAN)
        maps.addView(tmapView)
        tmap = TMapGpsManager(context)
        Log.d("#######", tmap!!.location.toString())
        tmap!!.OpenGps()

        val page = v.findViewById<LinearLayout>(R.id.page)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val list = v.findViewById<RecyclerView>(R.id.using_list_view)
        val accept = v.findViewById<Button>(R.id.accept)
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(context);
        list.setLayoutManager(mLayoutManager);

        //DB접근을 위한 Firebase 객체 선언 부분

        val db = Firebase.firestore
        auth = Firebase.auth
        val docRef = db.collection("pick_up_request")
        val docRef2 = db.collection("users")
        val docRef3 = db.collection("pickers")


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

                    val start = start_addr.substring(8,14)
                    val end = end_addr.substring(8,14)


                    val request_cost: String = document.data["pick_up_item_cost"].toString()
                    val document_id: String = document.id
                    val pick_up_flag : String = document.data["pick_up_check_flag"].toString()

                    pickList.apply {

                        add(pick_list(start, end, request_cost, document_id,pick_up_flag))

                    }

                }

                val adapter = PickUpListAdapter(pickList)
                var accept_doc_id: String? = "No id"

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



                        docRef.document(accept_doc_id!!).get().addOnSuccessListener { task ->
                            if (task.data!!.get("pick_up_check_flag") == "1"||task.data!!.get("pick_up_check_flag") == "2") {

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



                        docRef.document(accept_doc_id!!).update("pick_up_check_flag", "1")
                        docRef.document(accept_doc_id!!).update("uid_2", auth.currentUser!!.uid)
                        docRef3.document(auth.currentUser!!.uid).update("pick_up_list",FieldValue.arrayUnion(accept_doc_id))


                        val intent = Intent(context, BeforePickUpActivity::class.java)
                        intent.putExtra("Data",accept_doc_id)
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