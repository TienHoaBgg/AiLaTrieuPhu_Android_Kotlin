package com.nth.demoailatrieuphu.view

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.Utils.MediaManager
import com.nth.demoailatrieuphu.Utils.Utils
import com.nth.demoailatrieuphu.databinding.FragLawBinding
import com.nth.demoailatrieuphu.model.ItemBonus
import kotlinx.android.synthetic.main.dialog_ready.*
import java.io.IOException
import java.lang.Exception
import java.util.*

class LawFragment:Fragment() ,ContainActivity.IOnBackPressed{
    private lateinit var binding:FragLawBinding
    private lateinit var mediaPlay:MediaManager
    private lateinit var media:MediaPlayer
    private lateinit var random:Random
    private var isRun:Boolean = true
    private var thread:Thread?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragLawBinding.inflate(inflater,container,false)
        random = Random()
        inits()
        return binding.root
    }

    private fun inits(){
        mediaPlay = MediaManager()
        media = mediaPlay.setUri(this@LawFragment.context!!,"luatchoi_1")
        mediaPlay.start()
        isRun = true
        media.setOnCompletionListener {
            mediaPlay.release()
            binding.txtLevel15.background = null
           media = mediaPlay.setUri(this@LawFragment.context!!,"ready")
            mediaPlay.start()
            media.setOnCompletionListener {
                initDialogConfig()
            }
        }

        binding.btnContinue.setOnClickListener {
            isRun = false
            thread!!.interrupt()
            Utils.audioClick(context!!)
            try {
                if(mediaPlay.isPlay()!!){
                    mediaPlay.stop()
                }
                mediaPlay.release()
            }catch (e:Exception){}

        }

        thread = Thread{
            while (media.currentPosition < media.duration && isRun){
                if (media.currentPosition in 4800..5100){
                    requireActivity().runOnUiThread {
                        binding.txtLevel5.setBackgroundResource(R.drawable.bg_score)
                    }
                }else if (media.currentPosition in 5201..5499){
                    requireActivity().runOnUiThread {
                        binding.txtLevel10.setBackgroundResource(R.drawable.bg_score)
                        binding.txtLevel5.background = null
                    }
                }else if (media.currentPosition in 5880..6128){
                    requireActivity().runOnUiThread {
                        binding.txtLevel15.setBackgroundResource(R.drawable.bg_score)
                        binding.txtLevel10.background = null
                    }
                }
            }
            if (!isRun){
                activity!!.runOnUiThread {
                    initDialogConfig()
                }
            }
        }
        thread!!.start()


    }

    private fun initDialogConfig(){
        val dialogView = LayoutInflater.from(this@LawFragment.context).inflate(R.layout.dialog_ready,null)
        val builder = AlertDialog.Builder(this@LawFragment.context).setView(dialogView).show()
        builder.btn_Ready.setOnClickListener {
            Utils.audioClick(context!!)
            builder.dismiss()
                (activity as ContainActivity).openLawWaitFragment()
        }
        builder.btn_Cancel.setOnClickListener {
            Utils.audioClick(context!!)
            builder.dismiss()
            isRun = true
            (activity as ContainActivity).onBackPressed()
        }
    }

    override fun onDestroy() {
        mediaPlay.release()
        super.onDestroy()
    }



    override fun onBackPressed(): Boolean {
        return false
    }
}