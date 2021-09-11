package com.alfaradev.notification.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfaradev.notification.R
import com.alfaradev.notification.models.History
import kotlinx.android.synthetic.main.list_item_history.view.*

class HistoryAdapter(private val histories : MutableList<History>, private val context: Context)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(histories[position], context)

    override fun getItemCount(): Int = histories.size

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(history: History, context: Context){
            with(itemView){
                txt_body.text = history.body
            }
        }
    }

    fun updateList(c : List<History>){
        histories.clear()
        histories.addAll(c)
        notifyDataSetChanged()
    }

}