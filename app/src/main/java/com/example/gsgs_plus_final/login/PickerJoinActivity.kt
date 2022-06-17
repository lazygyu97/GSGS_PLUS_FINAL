package com.example.gsgs_plus_final.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PickerJoinActivity : AppCompatActivity() {

    //참고: lateinit으로 나중에 변수 초기화
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker_join)

        val goStudy = findViewById<Button>(R.id.picker_join_btn)
        val back = findViewById<Button>(R.id.btn_back)


        val db = Firebase.firestore
        val docRef = db.collection("users")


        //교육받으러가기 버튼
        goStudy.setOnClickListener {

            val picker_join_pwd_confirm = findViewById<EditText>(R.id.picker_join_confirm_pwd)

            auth = Firebase.auth
            val currentUser_email_addr = auth.currentUser!!.email.toString()
            Log.d("CurrentUser:", currentUser_email_addr)

            if (picker_join_pwd_confirm.text.toString().isNullOrBlank()) {
                Toast.makeText(this, "비밀번호를 입력하세요!", Toast.LENGTH_LONG).show()
//                Log.d("id:",docRef.document(currentUser_email_addr).id)
                Log.d("sss:",docRef.document(currentUser_email_addr).collection("pwd").get().toString())
                return@setOnClickListener
            }

            docRef.document(currentUser_email_addr).get().addOnSuccessListener {
                document -> if(document.data!!.containsValue(picker_join_pwd_confirm.text.toString())){

                    Toast.makeText(this,"비밀번호 인증성공!",Toast.LENGTH_LONG).show()
                    Log.d("data:",document.data.toString())
                }else{
                Toast.makeText(this,"비밀번호 인증실패!",Toast.LENGTH_LONG).show()
             }
            }


            back.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}