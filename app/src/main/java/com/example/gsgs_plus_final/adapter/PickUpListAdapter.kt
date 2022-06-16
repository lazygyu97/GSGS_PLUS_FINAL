package com.example.gsgs_plus_final.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gsgs_plus_final.R
import com.example.gsgs_plus_final.vo.pick_list

class PickUpListAdapter(private val items: ArrayList<pick_list>) :
    RecyclerView.Adapter<PickUpListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: pick_list, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PickUpListAdapter.ViewHolder, position: Int) {

        val item = items[position]
        val listener = View.OnClickListener { it ->
            if(position!= RecyclerView.NO_POSITION)
            {
                listener?.onItemClick(item,position)
            }
        }

        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.pickup_list_item, parent, false)
        return PickUpListAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: pick_list) {
            view.findViewById<TextView>(R.id.start).text = item.addr_start
            view.findViewById<TextView>(R.id.end).text = item.addr_end

            view.setOnClickListener(listener)
        }
    }
}
//class PickUpListAdapter(val context : Context, val pickList: ArrayList<pick_list>) : BaseAdapter() {
////
////    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
////        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
////        val view: View = LayoutInflater.from(context).inflate(R.layout.pickup_list_item, null)
////
////        val addr_start = view.findViewById<TextView>(R.id.start)
////        val addr_end = view.findViewById<TextView>(R.id.end)
////
////        val pick = pickList[p0]
////        addr_start.text = pick.addr_start
////        addr_end.text = pick.addr_end
////        return  view
////
////    }
////    override fun getItem(p0: Int): Any {
////      return pickList[p0]
////    }
////
////    override fun getItemId(p0: Int): Long {
////       return 0
////    }
////
////    override fun getCount(): Int {
////       return pickList.size
////    }
////}