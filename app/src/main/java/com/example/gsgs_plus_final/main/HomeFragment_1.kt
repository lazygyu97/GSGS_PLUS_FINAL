package com.example.gsgs_plus_final.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.request.DoingRequestActivity
import com.example.gsgs_plus_final.vo.PickUpRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment_1 : Fragment() {

    private var viewProfile: View? = null
    var pickImageFromAlbum = 0
    var fbStorage: FirebaseStorage? = null
    var uriPhoto: Uri? = null

    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home_1, container, false)
        val mainAct = activity as MainActivity


//        val mapView=v.findViewById<ConstraintLayout>(R.id.kakaoMapView)
//        val map = MapView(activity)
//        mapView.addView(map)


        val animation_1 = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        val animation_2 = AnimationUtils.loadAnimation(context, R.anim.translate_down)

        val db = Firebase.firestore
        auth = Firebase.auth

        val docRef = db.collection("pick_up_request")
        val docRef2 = db.collection("pickers")
        val docRef3 = db.collection("users")

        val page = v.findViewById<LinearLayout>(R.id.page)
        val pick_up_btn = v.findViewById<Button>(R.id.pick_up_btn)
        val close_btn = v.findViewById<TextView>(R.id.close_btn)
        val pl_pick = v.findViewById<Button>(R.id.pick_up_item_requestBtn)

        val pick_up_item_name = v.findViewById<EditText>(R.id.pick_up_item_name)
//      val pick_up_item_img = v.findViewById<ImageView>(R.id.pick_up_item_img)
        val pick_up_item_addr_start = v.findViewById<EditText>(R.id.pick_up_item_addr_start)
        val pick_up_item_addr_start_detail =
            v.findViewById<EditText>(R.id.pick_up_item_addr_start_detail)
        val pick_up_item_addr_end = v.findViewById<EditText>(R.id.pick_up_item_addr_end)
        val pick_up_item_addr_end_detaol =
            v.findViewById<EditText>(R.id.pick_up_item_addr_end_detail)
        val pick_up_item_request = v.findViewById<EditText>(R.id.pick_up_item_request)
        val pick_up_item_cost = v.findViewById<EditText>(R.id.pick_up_item_cost)
        val web_back = v.findViewById<LinearLayout>(R.id.web_back)
        val web_layout = v.findViewById<ConstraintLayout>(R.id.web_layout)
        val find_addr_1 = v.findViewById<Button>(R.id.find_addr_1)
        val find_addr_2 = v.findViewById<Button>(R.id.find_addr_2)
        val webview = v.findViewById<WebView>(R.id.webView)
        val pick_up_item_imgBtn_1 = v.findViewById<Button>(R.id.pick_up_item_imgBtn_1)
        val pick_up_item_imgBtn_2 = v.findViewById<Button>(R.id.pick_up_item_imgBtn_2)
        val pick_up_item_img = v.findViewById<ImageView>(R.id.pick_up_item_img)

        var webView: WebView? = null

        val handler = Handler()

        class WebAppInterface {
            @JavascriptInterface
            fun setAddress(arg1: String, arg2: String, arg3: String) {
                handler.post {
                    if (pick_up_item_addr_start.text.toString().length < 3) {
                        Log.d("여기봐", pick_up_item_addr_start.text.toString().length.toString())
                        web_back.visibility = View.GONE
                        web_layout.visibility = View.GONE
                        pick_up_item_addr_start.setText(
                            String.format("(%s) %s %s", arg1, arg2, arg3)
                        )
                    } else {
                        web_back.visibility = View.GONE
                        web_layout.visibility = View.GONE
                        pick_up_item_addr_end.setText(String.format("(%s) %s %s", arg1, arg2, arg3))
                    }

                }

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

            if (pick_up_item_name.text.toString().isNullOrBlank()) {
                Toast.makeText(context, "배송요청 물품이름을 입력하세요!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pick_up_item_addr_start.text.toString().isNullOrBlank()) {
                Toast.makeText(context, "출발지 주소를 입력하세요!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pick_up_item_addr_end.text.toString().isNullOrBlank()) {
                Toast.makeText(context, "도착지 주소를 입력하세요!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pick_up_item_cost.text.toString().isNullOrBlank()) {
                Toast.makeText(context, "예상비용을 입력하세요!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            docRef3.document(auth.currentUser!!.email.toString()).get().addOnSuccessListener {

                    document ->
                val result = makeRequestUid()
                val pick_up_request = PickUpRequest(
                    document.data!!.get("name").toString(),
                    auth.currentUser!!.email.toString(),
                    document.data!!.get("p_num").toString(),
                    auth.currentUser!!.uid,
                    pick_up_item_name.text.toString(),
                    pick_up_item_addr_start.text.toString() + pick_up_item_addr_start_detail.text.toString(),
                    pick_up_item_addr_end.text.toString() + pick_up_item_addr_end_detaol.text.toString(),
                    pick_up_item_request.text.toString(),
                    pick_up_item_cost.text.toString(),
                    "0"
                )
                val start = pick_up_item_addr_start.text.toString().substring(8, 14)
                Log.d("요청 중2 :", start)
                val end = pick_up_item_addr_end.text.toString().substring(8, 14)
                docRef.document(makeRequestUid()).set(pick_up_request)
                docRef3.document(auth.currentUser!!.email.toString()).update("pick_up_list", FieldValue.arrayUnion(makeRequestUid()))
                activity?.let {
                    val intent = Intent(context, DoingRequestActivity::class.java)

                    intent.putExtra("result", result)
                    intent.putExtra("start", start)
                    intent.putExtra("end", end)
                    startActivity(intent)
                }
            }


        }
        find_addr_1.setOnClickListener {

            web_back.visibility = View.VISIBLE
            web_layout.visibility = View.VISIBLE
            pick_up_item_addr_start.setText("")
            webView = webview
            WebView.setWebContentsDebuggingEnabled(true)
            webview.addJavascriptInterface(WebAppInterface(), "gsgs")

            webView!!.apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.setSupportMultipleWindows(true)
            }
            webView!!.loadUrl("https://gsgsaddr.web.app")
        }

        find_addr_2.setOnClickListener {

            web_back.visibility = View.VISIBLE
            web_layout.visibility = View.VISIBLE
            pick_up_item_addr_end.setText("")
            webView = webview
            WebView.setWebContentsDebuggingEnabled(true)
            webview.addJavascriptInterface(WebAppInterface(), "gsgs")

            webView!!.apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.setSupportMultipleWindows(true)
            }
            webView!!.loadUrl("https://gsgsaddr.web.app")
        }

        web_back.setOnClickListener {
            web_back.visibility = View.GONE
            web_layout.visibility = View.GONE
        }

        pick_up_item_imgBtn_1.setOnClickListener {

        }
        pick_up_item_imgBtn_2.setOnClickListener {
            //Open Album
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
        }


        // Inflate the layout for this fragment
        return v


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun makeRequestUid(): String {
        var date = Date()
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale("ko", "KR"))
        var formatted = formatter.format(date)

        return formatted

    }

}
