package com.navneet.kotlinlistview.adpter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.navneet.kotlinlistview.R
import com.navneet.kotlinlistview.model.Data
import kotlinx.android.synthetic.main.package_single_row.view.*

class DataAdapter(val activity:Activity,val dataen:List<Data> ?) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: Data) {
            itemView.tv_title.text=user.name


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.package_single_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataen?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataen!!.get(position))
    }

}