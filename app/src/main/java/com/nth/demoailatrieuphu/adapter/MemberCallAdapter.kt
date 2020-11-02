package com.nth.demoailatrieuphu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nth.demoailatrieuphu.databinding.ItemCallBinding
import com.nth.demoailatrieuphu.model.ItemCall


class MemberCallAdapter:RecyclerView.Adapter<MemberCallAdapter.ViewHolder> {
    private lateinit var inter:IMember
    constructor(inter:IMember){
        this.inter = inter
    }
    class ViewHolder:RecyclerView.ViewHolder{
         val dataBinding:ItemCallBinding
        constructor(dataBinding :ItemCallBinding):super(dataBinding.root){
            this.dataBinding = dataBinding
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemCallBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return inter.size()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.item = inter.getItem(position)
        holder.dataBinding.itemCall.setOnClickListener {
            inter.onClickItem(position,holder.dataBinding.itemCall)
        }
    }

    interface IMember{
        fun onClickItem(position: Int, v: View)
        fun getItem(position: Int):ItemCall
        fun size():Int
    }
}