package com.example.gsgs_plus_final.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.pickUp.BeforePickUpActivity
import com.example.gsgs_plus_final.request.DoingRequestActivity
import com.example.gsgs_plus_final.vo.PickUpRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.daum.mf.map.api.MapView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HomeFragment_1 : Fragment() {

    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_home_1, container, false)
        val mainAct = activity as MainActivity

//        val mapView=v.findViewById<ConstraintLayout>(R.id.kakaoMapView)
//        val map = MapView(activity)
//        mapView.addView(map)


        val animation_1 = AnimationUtils.loadAnimation(context,R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context,R.anim.translate_down)

        val db = Firebase.firestore
        auth = Firebase.auth
        val docRef = db.collection("pick_up_request")
        val docRef2 = db.collection("pickers")
        val docRef3 = db.collection("users")

        val page = v.findViewById<LinearLayout>(R.id.page)
        val pick_up_btn =v.findViewById<Button>(R.id.pick_up_btn)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val pl_pick = v.findViewById<Button>(R.id.pick_up_item_requestBtn)

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

            val pick_up_item_name = v.findViewById<EditText>(R.id.pick_up_item_name)
//            val pick_up_item_img = v.findViewById<ImageView>(R.id.pick_up_item_img)
            val pick_up_item_addr_start = v.findViewById<EditText>(R.id.pick_up_item_addr_start)
            val pick_up_item_addr_start_detail = v.findViewById<EditText>(R.id.pick_up_item_addr_start_detail)
            val pick_up_item_addr_end = v.findViewById<EditText>(R.id.pick_up_item_addr_end)
            val pick_up_item_addr_end_detaol = v.findViewById<EditText>(R.id.pick_up_item_addr_end_detail)
            val pick_up_item_request = v.findViewById<EditText>(R.id.pick_up_item_request)
            val pick_up_item_cost = v.findViewById<EditText>(R.id.pick_up_item_cost)



            if(pick_up_item_name.text.toString().isNullOrBlank()){
                Toast.makeText(context,"배송요청 물품이름을 입력하세요!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pick_up_item_addr_start.text.toString().isNullOrBlank()){
                Toast.makeText(context,"출발지 주소를 입력하세요!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pick_up_item_addr_end.text.toString().isNullOrBlank()){
                Toast.makeText(context,"도착지 주소를 입력하세요!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pick_up_item_cost.text.toString().isNullOrBlank()){
                Toast.makeText(context,"예상비용을 입력하세요!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            docRef3.document(auth.currentUser!!.email.toString()).get().addOnSuccessListener {

                document ->
                val pick_up_request = PickUpRequest(document.data!!.get("name").toString(),auth.currentUser!!.email.toString(),
                    document.data!!.get("p_num").toString(),auth.currentUser!!.uid,pick_up_item_name.text.toString(),pick_up_item_addr_start.text.toString()+pick_up_item_addr_start_detail.text.toString()
                            ,pick_up_item_addr_end.text.toString()+pick_up_item_addr_end_detaol.text.toString()
                            ,pick_up_item_request.text.toString(),pick_up_item_cost.text.toString(),"0")

                docRef.document(makeRequestUid()).set(pick_up_request)

            }

            activity?.let {
                val intent = Intent(context, DoingRequestActivity::class.java)
                startActivity(intent)
            }
        }


        // Inflate the layout for this fragment
        return v



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeRequestUid() : String{
        var date = Date()
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초",Locale("ko","KR"))
        var formatted = formatter.format(date)

        return formatted

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