package com.create.kotlin

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by 24540 on 2017/7/21.
 */
class ForecastListAdapter(var items: List<String>):
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent?.context))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text=items.get(position)
    }

    override fun getItemCount():  Int=items.size

    class ViewHolder(var textView:TextView):RecyclerView.ViewHolder(textView)
}