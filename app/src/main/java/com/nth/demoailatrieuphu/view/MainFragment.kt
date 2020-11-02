package com.nth.demoailatrieuphu.view

import android.media.MediaPlayer
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
import com.nth.demoailatrieuphu.Utils.Utils
import com.nth.demoailatrieuphu.databinding.FragBeginBinding
import com.nth.demoailatrieuphu.model.ItemBonus
import com.nth.demoailatrieuphu.model.ItemCall
import java.util.*

class MainFragment : Fragment(),ContainActivity.IOnBackPressed {
    lateinit var binding: FragBeginBinding
    lateinit var mediaPlayer: MediaManager
    private var backPress:Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragBeginBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {

        val animation =
            AnimationUtils.loadAnimation(this@MainFragment.context, R.anim.anim_rotate_circle)
        binding.animCircle.startAnimation(animation)
        mediaPlayer = MediaManager()
        mediaPlayer.setUri(this@MainFragment.context!!, "bgmusic")
        mediaPlayer.start()

        binding.btnPlay.setOnClickListener {
            if (mediaPlayer.isPlay()!!){
                mediaPlayer.stop()
            }
            Utils.audioClick(context!!)
            (activity as ContainActivity).openLawFragment()
        }

        binding.btnHightSore.setOnClickListener {
            if (mediaPlayer.isPlay()!!){
//                mediaPlayer.stop()
            }
            Utils.audioClick(context!!)
            val dialog = DialogHightScore()
            dialog.show(fragmentManager!!,DialogCallFragment::class.java.name)

        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onBackPressed(): Boolean {
        return if (backPress){
            backPress = false
            true
        }else{
            (activity as ContainActivity).clearBackStack()
            false
        }
    }


}