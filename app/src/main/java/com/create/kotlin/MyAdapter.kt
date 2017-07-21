package com.create.kotlin

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity


import kotlin.collections.ArrayList

/**
 * Created by 24540 on 2017/7/21.
 * 数据的适配器，简单实现
 */
class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var context:Context?=null
    private var list:ArrayList<Img> ?= null

    constructor(context: Context,list: ArrayList<Img>) : this() {
        this.context = context
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.text!!.text = list?.get(position)?.who
        Glide.with(context).load(list?.get(position)?.url).error(R.drawable.abc_ab_share_pack_mtrl_alpha).into(holder.image)
        holder.image.setOnClickListener {
            var io:String = list?.get(position)?.url.toString()
            context?.startActivity<NextActivity>((Pair("param",io)))
        }

    }

    override fun getItemCount(): Int {
        return list?.size as Int
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image:ImageView
        var text :TextView

        init {
            image = view.findViewById(R.id.image)
            text  = view.findViewById(R.id.text)
        }
    }
}