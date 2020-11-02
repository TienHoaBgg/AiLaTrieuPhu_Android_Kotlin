package com.nth.demoailatrieuphu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nth.demoailatrieuphu.databinding.ItemListviewHightscoreBinding
import com.nth.demoailatrieuphu.model.ItemScore

class HighScoreAdapter:RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    private  var listHighScore:MutableList<ItemScore> = mutableListOf()

    private lateinit var binding: ItemListviewHightscoreBinding
    constructor(listScore:MutableList<ItemScore>){
        this.listHighScore = listScore
    }

    class ViewHolder:RecyclerView.ViewHolder{

        var binding:ItemListviewHightscoreBinding

        constructor(binding: ItemListviewHightscoreBinding):super(binding.root){
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreAdapter.ViewHolder {
         binding = ItemListviewHightscoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return listHighScore.size
    }

    override fun onBindViewHolder(holder: HighScoreAdapter.ViewHolder, position: Int) {
        holder.binding.item = listHighScore[position]
    }
}