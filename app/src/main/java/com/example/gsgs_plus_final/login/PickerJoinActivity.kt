package com.example.gsgs_plus_final.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.main.MainActivity

class PickerJoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker_join)

        val goStudy = findViewById<Button>(R.id.btn_join)
        val back =findViewById<Button>(R.id.btn_back)
        goStudy.setOnClickListener {
            val intent = Intent(this, PickerJoinStudyActivity::class.java)
            startActivity(intent)

        }
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}