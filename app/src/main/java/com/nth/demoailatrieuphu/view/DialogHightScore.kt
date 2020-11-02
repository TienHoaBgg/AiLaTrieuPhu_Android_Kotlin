package com.nth.demoailatrieuphu.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.Utils.Utils
import com.nth.demoailatrieuphu.adapter.HighScoreAdapter
import com.nth.demoailatrieuphu.adapter.MemberCallAdapter
import com.nth.demoailatrieuphu.databinding.DialogCallBinding
import com.nth.demoailatrieuphu.databinding.FragListHightscoreBinding
import com.nth.demoailatrieuphu.model.ItemCall
import com.nth.demoailatrieuphu.model.ItemScore

class DialogHightScore : DialogFragment() {
    private lateinit var binding: FragListHightscoreBinding
    private lateinit var listHighScore: MutableList<ItemScore>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragListHightscoreBinding.inflate(LayoutInflater.from(context), container, false)

        listHighScore = mutableListOf()
        val data = context!!.getSharedPreferences("appTP", Context.MODE_PRIVATE)
        val itemScore1 = ItemScore(
            data.getString("Name5", "")!!,
            data.getString("Money5", "")!!,
            "Câu " + data.getInt("Level5", 0)
        )
        if (itemScore1.name != "") {
            listHighScore.add(itemScore1)
        }
        val itemScore2 = ItemScore(
            data.getString("Name4", "")!!,
            data.getString("Money4", "")!!,
            "Câu " + data.getInt("Level4", 0)
        )
        if (itemScore2.name != "") {
            listHighScore.add(itemScore2)
        }
        val itemScore3 = ItemScore(
            data.getString("Name3", "")!!,
            data.getString("Money3", "")!!,
            "Câu " + data.getInt("Level3", 0)
        )
        if (itemScore3.name != "") {
            listHighScore.add(itemScore3)
        }
        val itemScore4 = ItemScore(
            data.getString("Name2", "")!!,
            data.getString("Money2", "")!!,
            "Câu " + data.getInt("Level2", 0)
        )
        if (itemScore4.name != "") {
            listHighScore.add(itemScore4)
        }

        val itemScore5 = ItemScore(
            data.getString("Name1", "")!!,
            data.getString("Money1", "")!!,
            "Câu " + data.getInt("Level1", 0)
        )
        if (itemScore5.name != "") {
            listHighScore.add(itemScore5)
        }

        binding.listScore.layoutManager = LinearLayoutManager(context)
        binding.listScore.adapter = HighScoreAdapter(listHighScore)

        binding.btnOk.setOnClickListener {
            Utils.audioClick(context!!)
            this.dismiss()
        }
        return binding.root
    }
}