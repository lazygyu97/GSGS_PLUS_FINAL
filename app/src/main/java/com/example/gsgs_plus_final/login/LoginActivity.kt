package com.example.gsgs_plus_final.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var isDouble = false


    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val db = Firebase.firestore
        val docRef = db.collection("users")

        //로그인 버튼 눌렀을때 메인화면으로 넘어갈때 나오는 토스트 메세지
        val btn_login1 = findViewById<Button>(R.id.btn_login)
        btn_login1.setOnClickListener {

            val login_id = findViewById<EditText>(R.id.edit_id)
            val login_pwd = findViewById<EditText>(R.id.edit_pw)

            if(login_id.text.toString().isNullOrBlank()){
                Toast.makeText(this, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(login_pwd.text.toString().isNullOrBlank()){
                Toast.makeText(this, "비밀번호를 입력해주세요! ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(login_id.text.toString(),login_pwd.text.toString()).addOnCompleteListener(this){
                task -> if(task.isSuccessful){

                    Log.d(TAG,"Success")
                    val user = auth.currentUser
                    Toast.makeText(this, "메인화면으로 이동합니다. ", Toast.LENGTH_SHORT).show()
                    //로그인 버튼을 누르면 메인화면으로 넘어가는 이벤트
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)


               }else{

                if(docRef.document(login_id.text.toString()) == null){
                    Log.w(TAG,"loginIdFail",task.exception)
                    Toast.makeText(this, "등록된 ID가 아닙니다! ", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }else{
                    if(!(login_pwd.text.toString().equals(docRef.document(login_id.text.toString()).collection("pwd")))){
                        Log.w(TAG,"loginIdpwd",task.exception)
                        Toast.makeText(this, "패스워드가 틀립니다! ", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }//if

                }//else

               }//else
            }
        }

        //회원가입 글자를 누르면 회원가입화면으로 넘어가는 이벤트
        val start_join1 = findViewById<TextView>(R.id.start_join)
        start_join1.setOnClickListener {

            var intent2 = Intent(this, JoinActivity::class.java)
            startActivity(intent2)
        }

        //회원가입 글자를 누르면 ID/PW찾기 화면으로 넘어가는 이벤트
        val find_IdPw1 = findViewById<TextView>(R.id.text_IdPwfind)
        find_IdPw1.setOnClickListener {

            var intent2 = Intent(this, FindActivity::class.java)
            startActivity(intent2)
        }

    }

    override fun onBackPressed() {

        if(isDouble == true){
            finish()
        }

        isDouble = true
        Toast.makeText(this, "종료하시려면 더블클릭", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable{
            isDouble = false
        }, 1000)
    }

}