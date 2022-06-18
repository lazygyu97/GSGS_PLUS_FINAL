package com.example.gsgs_plus_final.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import android.widget.*
import androidx.annotation.RequiresApi

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.*

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

import com.google.firebase.firestore.remote.ConnectivityMonitor
import net.daum.mf.map.api.MapView
import java.lang.reflect.Array.get

class HomeFragment_1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v = inflater.inflate(R.layout.fragment_home_1, container, false)
        val mainAct = activity as MainActivity

        var webView: WebView? = null

        //val mapView=v.findViewById<ConstraintLayout>(R.id.kakaoMapView)
        //val map = MapView(activity)
        //mapView.addView(map)



        val db = Firebase.firestore
        auth = Firebase.auth
        val docRef = db.collection("pick_up_request")
        val docRef2 = db.collection("pickers")
        val docRef3 = db.collection("users")

        val page = v.findViewById<LinearLayout>(R.id.page)
        val pick_up_btn =v.findViewById<Button>(R.id.pick_up_btn)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val pl_pick = v.findViewById<Button>(R.id.pick_up_item_requestBtn)

        val animation_1 = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context, R.anim.translate_down)

        val webview = v.findViewById<WebView>(R.id.webView)
        val web_back = v.findViewById<LinearLayout>(R.id.web_back)
        val web_layout = v.findViewById<ConstraintLayout>(R.id.web_layout)

        val page = v.findViewById<FrameLayout>(R.id.page)
        val pick_up_btn = v.findViewById<Button>(R.id.pick_up_btn)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val pl_pick = v.findViewById<Button>(R.id.pl_pick)
        val find_addr = v.findViewById<Button>(R.id.find_addr)

        class WebAppInterface{

            /** Show a toast from the web page  */
            @JavascriptInterface
            fun setAddress(arg1:String,arg2:String,arg3:String) {
                v.findViewById<EditText>(R.id.addr_1).setText(String.format("(%s) %s %s",arg1,arg2,arg3))
            }
        }



        pick_up_btn.setOnClickListener {
            pick_up_btn.visibility = View.INVISIBLE
            mainAct.HideBottomNavi(true)
            page.startAnimation(animation_1)
            page.visibility = View.VISIBLE
        }
        close_btn.setOnClickListener {
            pick_up_btn.visibility = View.VISIBLE

            mainAct.HideBottomNavi(false)
            page.startAnimation(animation_2)
            page.visibility = View.INVISIBLE
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

        find_addr.setOnClickListener {

            web_back.visibility=View.VISIBLE
            web_layout.visibility=View.VISIBLE

            webView = webview
            WebView.setWebContentsDebuggingEnabled(true)
            webview.addJavascriptInterface(WebAppInterface(),"gsgs")

            webView!!.apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.setSupportMultipleWindows(true)
            }
            webView!!.loadUrl("https://gsgsaddr.web.app")
        }

        web_back.setOnClickListener{
            web_back.visibility=View.GONE
            web_layout.visibility=View.GONE
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
}

