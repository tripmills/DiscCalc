package com.example.disccalc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultAdapter(private var items: List<ResultItem>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val toplabel: TextView = view.findViewById(R.id.toplabel)
        val label: TextView = view.findViewById(R.id.label)
        val value: TextView = view.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.toplabel.text = items[position].toplabel
        holder.label.text = items[position].label
        holder.value.text = items[position].value
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<ResultItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}