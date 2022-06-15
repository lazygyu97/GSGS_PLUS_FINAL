package com.example.gsgs_plus_final.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.vo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var double_check_confirm = "no"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)


        val db = Firebase.firestore
        val docRef = db.collection("users")

        auth = Firebase.auth
        val double_check = findViewById<Button>(R.id.btn_doubleCheck)
        val join_button = findViewById<Button>(R.id.btn_join)

        //중복확인
        double_check.setOnClickListener {

            val join_id = findViewById<EditText>(R.id.join_id)

            if(join_button.text.toString().isNullOrBlank()){
                Toast.makeText(this,"아이디를 입력하세요!!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val query = docRef.document(join_id.text.toString()).get()

            query.addOnSuccessListener {
                document -> if(!document.exists()){
                    this.double_check_confirm = "yes"
                Toast.makeText(this,"사용 가능한 ID입니다.",Toast.LENGTH_LONG).show()
               }else{
                   this.double_check_confirm = "no"
                    Toast.makeText(this,"중복된 ID입니다.",Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
              }
            }.addOnFailureListener{
                exception ->
                Log.d(TAG,"중복체크 로직 오류 ",exception)
            }
        }


        join_button.setOnClickListener {

            try {

                val join_check = findViewById<CheckBox>(R.id.join_check)
                val join_name = findViewById<EditText>(R.id.join_name)
                val join_sub_name = findViewById<EditText>(R.id.join_sub_name)
                val join_id = findViewById<EditText>(R.id.join_id)
                val join_pwd = findViewById<EditText>(R.id.join_pwd)
                val join_confirm_pwd = findViewById<EditText>(R.id.join_confirm_pwd)

                join_check.setOnCheckedChangeListener { buttonView, isChecked ->

                    if(isChecked){
                        join_check.text = " 동의"
                    }else{
                        join_check.text = "반대"
                    }
                }

                val user = User(join_name.text.toString(),join_sub_name.text.toString(),join_id.text.toString(),
                    join_pwd.text.toString())

                if(user.name.isNullOrBlank()){
                    //|| !(Pattern.matches("^[가-힣]*$",user.name)) 한글 입력 조건
                    Toast.makeText(this,"이름이 공백입니다!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener

                }

                if(1 >= user.sub_name.length || user.sub_name.length >= 9){
                    Toast.makeText(this,"닉네임의 길이는 최소2글자 최대 8글자 입니다!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener

                }

                if(user.id.length > 20 ){
                    Toast.makeText(this,"아이디가 20글자를 초과했어요!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                //비밀번호 확인
                if(join_pwd.text.toString() != join_confirm_pwd.text.toString()){
                    Toast.makeText(this,"비밀번호가 맞지 않습니다!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if(!(join_check.isChecked)){
                    Toast.makeText(this,"이용약관에 동의해주세요!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if(double_check_confirm == "no") {
                    Toast.makeText(this,"중복체크 해주세요!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }else{

                    auth.createUserWithEmailAndPassword(join_id.text.toString(),join_pwd.text.toString())
                        .addOnCompleteListener(this){
                                task -> if(task.isSuccessful){
                            docRef.document(user.id).set(user)
                            Toast.makeText(this,"회원가입 성공!",Toast.LENGTH_LONG).show()

                            val intent = Intent(this, JoinFinshActivity::class.java)
                            intent.putExtra("user_name",user.name)
                            startActivity(intent)

                        }else{

                                Toast.makeText(this, "회원가입 실패!", Toast.LENGTH_LONG).show()
                                Log.d(TAG, "가입 실패 오류:", task.exception)


                             }

                        }

                }



            }catch (exception: Exception){

                val join_name = findViewById<EditText>(R.id.join_name)
                val join_sub_name = findViewById<EditText>(R.id.join_sub_name)
                val join_id = findViewById<EditText>(R.id.join_id)
                val join_pwd = findViewById<EditText>(R.id.join_pwd)



                val user = User(join_name.text.toString(),join_sub_name.text.toString(),join_id.text.toString(),
                    join_pwd.text.toString())


                if(!(Pattern.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+$",user.id))){
                    Toast.makeText(this,"아이디가 이메일 형식이 아닙니다!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                Toast.makeText(this, "필수사항을 모두 입력하세요!", Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }
        }






        val btn_back = findViewById<Button>(R.id.btn_back)
        btn_back.setOnClickListener {
            //뒤로가기
            var intent1 = Intent(this, LoginActivity::class.java)
            startActivity(intent1)
        }


    }
}