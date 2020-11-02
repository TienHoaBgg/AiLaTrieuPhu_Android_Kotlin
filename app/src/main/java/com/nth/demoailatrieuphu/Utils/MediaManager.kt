package com.nth.demoailatrieuphu.Utils

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.nth.demoailatrieuphu.Utils.Utils.Companion.getRawUri
import java.net.URI

class MediaManager :MediaPlayer.OnErrorListener{
    private var  path:String? = null
    private var media:MediaPlayer ? =null
    private var isLoop:Boolean = false
    fun setUri(context:Context,path:String):MediaPlayer{
        val url:Uri = getRawUri(context,path)!!
        media = MediaPlayer()
        media?.setOnErrorListener(this)
        media?.setDataSource(context,url)
        media?.prepare()
        media?.isLooping = isLoop
       return media!!
    }
    fun start(){
        media?.start()
    }

    fun pause(){
        media?.pause()
    }
    fun stop(){
        media?.stop()
    }
    fun release(){
        media?.release()
    }
    fun isPlay(): Boolean? {
        return media?.isPlaying
    }
    fun  isLoop(loop:Boolean){
        media?.isLooping = loop
    }
    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

}
