package com.ajindustries.scanit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataRecyclerAdapter(val context: Context, val itemList: List<BookEntity>) : RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder>(){
    class DataViewHolder(view:View): RecyclerView.ViewHolder(view){
        val textView : TextView= view.findViewById(R.id.txtRecyclerRowItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_single_row,parent,false)

        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val text = itemList[position].enrollno
        holder.textView.text = text
    }
}