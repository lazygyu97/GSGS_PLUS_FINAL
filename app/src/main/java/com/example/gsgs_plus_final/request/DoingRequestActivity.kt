package com.example.gsgs_plus_final.request

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
private lateinit var mDbRef: DatabaseReference

class DoingRequestActivity : AppCompatActivity() {

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doing_request)

        val db = Firebase.firestore
        auth = Firebase.auth


        val name = intent.getStringExtra("result")
        val start = intent.getStringExtra("start")
        val end = intent.getStringExtra("end")

        Log.d("요청 중1:", name.toString())
        Log.d("요청 중2 :", start.toString())
        Log.d("요청 중3 :", end.toString())



        val txt_start = findViewById<TextView>(R.id.start)
        val txt_end = findViewById<TextView>(R.id.end)

        txt_start.setText(start.toString())
        txt_end.setText(end.toString())

        val docRef = db.collection("pick_up_request")
        val btn_back = findViewById<Button>(R.id.btn_back)


        docRef.document(name.toString()).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val result = snapshot.data!!.get("pick_up_check_flag").toString()

                if (result == "1") {
                    val intent = Intent(this, SuccessRequestActivity::class.java)
                    intent.putExtra("start",start)
                    intent.putExtra("end",end)
                    startActivity(intent)


                } else {
                    Log.d("change fail!!!!!!!!!!!", "슬프다.")
                    return@addSnapshotListener
                }

            } else {
                Log.d(TAG, "Current data: null")
            }

        }

        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }
    }
}