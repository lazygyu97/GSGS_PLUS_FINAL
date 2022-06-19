package com.example.gsgs_plus_final.request

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.chat.ChatUser
import com.example.gsgs_plus_final.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SuccessRequestActivity : AppCompatActivity() {
    private lateinit var mDbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onBackPressed() {
       // super.onBackPressed()
    }

    fun Chat(arrayList: java.util.ArrayList<*>) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_request)

        val db = Firebase.firestore
        auth = Firebase.auth

        val start = intent.getStringExtra("start")
        val end = intent.getStringExtra("end")
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        val txt_start = findViewById<TextView>(R.id.start)
        val txt_end = findViewById<TextView>(R.id.end)
        val btn_back = findViewById<Button>(R.id.btn_back)

        txt_start.setText(start.toString())
        txt_end.setText(end.toString())

        val docRef=db.collection("users")
        val docRef2=db.collection("pickers")

        val user_data = docRef.document(id.toString())

        user_data.get().addOnSuccessListener {
            document->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                val pick_up_list=document["pick_up_list"] as ArrayList<*>
                Log.d(TAG, "pick_up_list: ${pick_up_list[0]}")
                for(i in pick_up_list){

                    docRef2.document().get()
                    Log.d(TAG, "pick_up_list!!! $i")
                }

            } else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }


        //실시간 디비에 이름하고 uid 추가
        //addUserToDatabase(user.name,auth.currentUser?.uid!!)


        btn_back.setOnClickListener {
            Toast.makeText(this,"홈 화면으로 이동합니다!",Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }

    private fun addUserToDatabase(name: String,uid: String){
        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(ChatUser(name,uid))

    }
}