package com.example.gsgs_plus_final.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.pickUp.BeforePickUpActivity
import com.example.gsgs_plus_final.request.DoingRequestActivity
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
}
