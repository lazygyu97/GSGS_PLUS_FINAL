package com.example.gsgs_plus_final.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.chat.ChatActivity
import com.example.gsgs_plus_final.chat.ChatUser
import com.example.gsgs_plus_final.vo.User

class ChatUserAdapter(val context: Context?, val ChatUserList: ArrayList<ChatUser>):

    RecyclerView.Adapter<ChatUserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.chat_user_layout,parent,false)
        Log.e("TAG","ChatUserAdapter에서 CreateViewHolder 시작")
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser=ChatUserList[position]
        holder.textname.text = currentUser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(this.context, ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ChatUserList.size
    }

    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textname= itemView.findViewById<TextView>(R.id.txt_name)

    }
}