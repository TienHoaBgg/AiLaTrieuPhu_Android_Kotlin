package com.nth.demoailatrieuphu.view

import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.Utils.GetQuestionSQL
import com.nth.demoailatrieuphu.Utils.MediaManager
import com.nth.demoailatrieuphu.databinding.FragWaitLawBinding
import com.nth.demoailatrieuphu.model.ItemQuestion
import com.nth.demoailatrieuphu.model.StatusHelp

class LawWaitFragment : Fragment(), ContainActivity.IOnBackPressed {
    lateinit var binding: FragWaitLawBinding
    lateinit var mediaPlayer: MediaManager
    private var listQuestion: ArrayList<ItemQuestion> = arrayListOf()
    private lateinit var statusHelp: StatusHelp
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragWaitLawBinding.inflate(inflater, container, false)
        statusHelp = StatusHelp(false,false,false,false)
        inits()
        return binding.root
    }

    private fun inits() {

        val animation = AnimationUtils.loadAnimation(this@LawWaitFragment.context, R.anim.anim_rotate_waite)
        binding.imgLoad.startAnimation(animation)

        val database =
            GetQuestionSQL(this@LawWaitFragment.context!!)
        listQuestion = database.query15Question()
        mediaPlayer = MediaManager()
        var media = mediaPlayer.setUri(context!!, "gofind")
        mediaPlayer.start()
        media.setOnCompletionListener {
            mediaPlayer.stop()
            mediaPlayer.release()
            (activity as ContainActivity).openQuestionFragment(1, statusHelp,listQuestion)
        }
    }

    override fun onBackPressed(): Boolean {
        return if(mediaPlayer.isPlay()!!){
            Toast.makeText(this@LawWaitFragment.context,"Can't Back...",Toast.LENGTH_SHORT).show()
            true
        }else{
            false
        }
    }

}