package com.example.gsgs_plus_final.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gsgs_plus_final.R

class IdFindFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_find_finish)

        val btn_back = findViewById<Button>(R.id.btn_back)
        btn_back.setOnClickListener {
            //뒤로가기
            var intent1 = Intent(this, LoginActivity::class.java)
            startActivity(intent1)
        }
    }
}