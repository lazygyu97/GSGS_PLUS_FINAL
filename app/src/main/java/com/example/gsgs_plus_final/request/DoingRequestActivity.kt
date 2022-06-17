package com.example.gsgs_plus_final.request

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.main.MainActivity

class DoingRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doing_request)
        val btn_back=findViewById<Button>(R.id.btn_back)
        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}