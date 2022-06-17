package com.example.gsgs_plus_final.pickUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gsgs_plus_final.R

class DoingPickUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doing_pick_up)


        val btn_finish = findViewById<Button>(R.id.btn_finish)

        btn_finish.setOnClickListener {
            val intent = Intent(this, FinishPickUpActivity::class.java)
            startActivity(intent)
        }
    }
}