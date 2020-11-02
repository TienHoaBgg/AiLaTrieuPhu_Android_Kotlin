package com.nth.demoailatrieuphu.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.Utils.GetQuestionSQL
import com.nth.demoailatrieuphu.Utils.MediaManager
import com.nth.demoailatrieuphu.databinding.FragLawBinding
import com.nth.demoailatrieuphu.model.ItemBonus
import com.nth.demoailatrieuphu.model.ItemQuestion
import com.nth.demoailatrieuphu.model.StatusHelp

class QuestionFragment : Fragment(),ContainActivity.IOnBackPressed {
    lateinit var binding: FragLawBinding
    private var levelQuestion: Int = 0
    private lateinit var mediaPlayer: MediaManager
    private lateinit var statusHelp: StatusHelp
    private var listQuestion:ArrayList<ItemQuestion> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragLawBinding.inflate(inflater, container, false)
        binding.btnContinue.visibility = View.INVISIBLE
        val bundle = arguments
        if (bundle!=null) {
            levelQuestion = bundle.getInt("LEVEL")
            statusHelp = bundle.getParcelable<StatusHelp>("HELP")!!
            listQuestion = bundle.getParcelableArrayList<ItemQuestion>("LIST")!!
        }
        inits(levelQuestion)
        return binding.root
    }

    private fun inits(level: Int) {
        mediaPlayer = MediaManager()
        when (level) {
            1 -> {
                changeQuestion(binding.txtLevel1,binding.txtLevel1, "ques1")
            }
            2 -> {
                changeQuestion(binding.txtLevel1,binding.txtLevel2, "ques2")
            }
            3 -> {
                changeQuestion(binding.txtLevel2,binding.txtLevel3, "ques3")
            }
            4 -> {
                changeQuestion(binding.txtLevel3,binding.txtLevel4, "ques4")
            }
            5 -> {
                changeQuestion(binding.txtLevel4,binding.txtLevel5, "ques5")
            }
            6 -> {
                changeQuestion(binding.txtLevel5,binding.txtLevel6, "ques6")
            }
            7 -> {
                changeQuestion(binding.txtLevel6,binding.txtLevel7, "ques7")
            }
            8 -> {
                changeQuestion(binding.txtLevel7,binding.txtLevel8, "ques8")
            }
            9 -> {
                changeQuestion(binding.txtLevel8,binding.txtLevel9, "ques9")
            }
            10 -> {
                changeQuestion(binding.txtLevel9, binding.txtLevel10, "ques10")
            }
            11 -> {
                changeQuestion(binding.txtLevel10,binding.txtLevel11, "ques11")
            }
            12 -> {
                changeQuestion(binding.txtLevel11,binding.txtLevel12, "ques12")
            }
            13 -> {
                changeQuestion(binding.txtLevel12,binding.txtLevel13, "ques13")
            }
            14 -> {
                changeQuestion(binding.txtLevel13,binding.txtLevel14, "ques14")
            }
            15 -> {
                changeQuestion(binding.txtLevel14,binding.txtLevel15, "ques15")
            }
        }
    }

    private fun changeQuestion(txtLastLevel:TextView,txtLevel: TextView, nameAudio: String) {
        txtLevel.setBackgroundResource(R.drawable.bg_score)
        val media = mediaPlayer.setUri(context!!, nameAudio)
        mediaPlayer.start()
        media.setOnCompletionListener {
            mediaPlayer.release()
            (activity as ContainActivity).backPlayFragment(txtLastLevel.text.toString(),txtLevel.text.toString(),levelQuestion,statusHelp,listQuestion)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onBackPressed(): Boolean {
        return if(mediaPlayer.isPlay()!!){
            Toast.makeText(this@QuestionFragment.context,"Can't Back...", Toast.LENGTH_SHORT).show()
            true
        }else{
            false
        }
    }

}