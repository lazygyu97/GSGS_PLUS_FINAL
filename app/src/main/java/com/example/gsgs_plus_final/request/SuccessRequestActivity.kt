package com.example.gsgs_plus_final.request

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.main.MainActivity

class SuccessRequestActivity : AppCompatActivity() {
    override fun onBackPressed() {
       // super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_request)

        val start = intent.getStringExtra("start")
        val end = intent.getStringExtra("end")

        val txt_start = findViewById<TextView>(R.id.start)
        val txt_end = findViewById<TextView>(R.id.end)
        val btn_back = findViewById<Button>(R.id.btn_back)

        txt_start.setText(start.toString())
        txt_end.setText(end.toString())

        btn_back.setOnClickListener {
            Toast.makeText(this,"홈 화면으로 이동합니다!",Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
}