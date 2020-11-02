package com.nth.demoailatrieuphu.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.Utils.GetQuestionSQL
import com.nth.demoailatrieuphu.Utils.MediaManager
import com.nth.demoailatrieuphu.Utils.Utils
import com.nth.demoailatrieuphu.databinding.FragPlayBinding
import com.nth.demoailatrieuphu.model.ItemBonus
import com.nth.demoailatrieuphu.model.ItemQuestion
import com.nth.demoailatrieuphu.model.StatusHelp
import com.nth.demoailatrieuphu.model.TrueCase
import kotlinx.android.synthetic.main.dialog_help_audience.*
import kotlinx.android.synthetic.main.dialog_save_user.*
import kotlinx.android.synthetic.main.dialog_save_user.btn_Cancel
import kotlinx.android.synthetic.main.dialog_save_user.btn_Save
import kotlinx.android.synthetic.main.dialog_save_user.txt_Money
import kotlinx.android.synthetic.main.dialog_terminal.btn_Ok
import java.util.*


class PlayFragment : Fragment(), ContainActivity.IOnBackPressed {
    lateinit var binding: FragPlayBinding
    private var levelQuestion: Int = 0
    lateinit var itemQuestion: ItemQuestion
    var listQuestion: ArrayList<ItemQuestion> = arrayListOf()
    private lateinit var mediaPlayer: MediaManager
    private var media: MediaPlayer? = null
    private lateinit var mediaBg: MediaPlayer
    private lateinit var trueCase: TrueCase
    private val bonus: ItemBonus = ItemBonus()
    private var money: String? = null
    private var lastMoney: String = ""
    private var stStop: Boolean = false
    private lateinit var statusHelp: StatusHelp
    private lateinit var rd: Random
    private var timePlay: Int = 30
    private var isPlay: Boolean = true

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragPlayBinding.inflate(inflater, container, false)
        val bundle = arguments
        mediaPlayer = MediaManager()
        timePlay = 30
        isPlay = true
        if (bundle != null) {
            levelQuestion = bundle.getInt("LEVEL")
            money = bundle.getString("MONEY")
            lastMoney = bundle.getString("LAST_MONEY").toString()
            statusHelp = bundle.getParcelable<StatusHelp>("HELP")!!
            listQuestion = bundle.getParcelableArrayList<ItemQuestion>("LIST")!!
        }
        rd = Random()
        money = money!!.trim().substring(2)
        binding.txtMoney.text = money
        itemQuestion = listQuestion[levelQuestion - 1]
        binding.item = itemQuestion
        if (levelQuestion == 15) {
            playAudioQuestion15()
        }
        mediaBg = MediaPlayer.create(context, R.raw.background_music)
        mediaBg.isLooping = true
        mediaBg.start()
        inits()
        initTimeUI(30)
        initsTimePlay().execute()
        return binding.root
    }

    private fun inits() {
        trueCase()
        initIconBTN()
        Log.i("Test", itemQuestion.trueCase)
        binding.txtAnsA.setOnClickListener {
            checkAns(binding.txtAnsA, "ans_a", "true_a")
        }

        binding.txtAnsB.setOnClickListener {
            checkAns(binding.txtAnsB, "ans_b", "true_b")
        }

        binding.txtAnsC.setOnClickListener {
            checkAns(binding.txtAnsC, "ans_c", "true_c")
        }
        binding.txtAnsD.setOnClickListener {
            checkAns(binding.txtAnsD, "ans_d", "true_d2")
        }

        binding.btnCall.setOnClickListener {
            initIconBTN()
            Utils.audioClick(context!!)

            if (statusHelp.stCall) {
                initDialogNotiHelp()
            } else {
                playAudioHelp("help_call")
                statusHelp.stCall = true
                initCall()
            }
        }
        binding.btnHelp.setOnClickListener {
            initIconBTN()
            Utils.audioClick(context!!)


            if (statusHelp.stHelp) {
                initDialogNotiHelp()
            } else {
                var me = playAudioHelp("khan_gia")
                me.setOnCompletionListener {
                    statusHelp.stHelp = true
                    initHelp()
                }
            }
        }
        binding.btnPercent50.setOnClickListener {
            initIconBTN()
            Utils.audioClick(context!!)

            if (statusHelp.stPercent50) {
                initDialogNotiHelp()
            } else {
                val me = playAudioHelp("sound5050")
                me.setOnCompletionListener {
                    statusHelp.stPercent50 = true
                    initPercent()
                }
            }
        }
        binding.btnRestart.setOnClickListener {
            initIconBTN()
            Utils.audioClick(context!!)
            if (statusHelp.stReset) {
                initDialogNotiHelp()
            } else {
                statusHelp.stReset = true
                initChanges()
            }
        }
        binding.btnTerminate.setOnClickListener {
            initIconBTN()
            Utils.audioClick(context!!)
            if (stStop) {
                initDialogNotiHelp()
            } else {
                playAudioHelp("lose2")
                stStop = true
                initStop()
            }
        }
    }

    private fun playAudioHelp(nameAudio: String): MediaPlayer {
        mediaPlayer.stop()
        val m = mediaPlayer.setUri(context!!, nameAudio)
        mediaPlayer.isLoop(false)
        mediaPlayer.start()
        return m
    }

    private fun initCall() {
        binding.btnCall.setImageResource(R.drawable.bg_faile_call)
        val trueAns = Integer.parseInt(itemQuestion.trueCase)
        val percentHelp = getHelpPercent(trueAns, levelQuestion)
        var ans = ""
        when (percentHelp.max()) {
            percentHelp[0] -> {
                ans = "chọn đáp án A"
            }
            percentHelp[1] -> {
                ans = "chọn đáp án B"
            }
            percentHelp[2] -> {
                ans = "chọn đáp án C"
            }
            percentHelp[3] -> {
                ans = "chọn đáp án D"
            }
        }
        val dialog = DialogCallFragment(ans)
        dialog.show(fragmentManager!!, DialogCallFragment::class.java.name)
    }

    private fun initHelp() {
        binding.btnHelp.setImageResource(R.drawable.bg_faile_help)
        val dialogView =
            LayoutInflater.from(this@PlayFragment.context)
                .inflate(R.layout.dialog_help_audience, null)
        val builder = AlertDialog.Builder(this@PlayFragment.context).setView(dialogView).show()
        val trueAns = Integer.parseInt(itemQuestion.trueCase)
        val dataPercent = getHelpPercent(trueAns, levelQuestion)
        val entries: MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, dataPercent[0]))
        entries.add(BarEntry(1f, dataPercent[1]))
        entries.add(BarEntry(2f, dataPercent[2]))
        entries.add(BarEntry(3f, dataPercent[3]))

        val set = BarDataSet(entries, "")
        val data = BarData(set)
        val description = Description()
        description.text = "Ý kiến khán giả"
        data.barWidth = 0.9f
        builder.barChart.data = data
        builder.barChart.description = description
        builder.barChart.setFitBars(true)
        builder.barChart.invalidate()

        builder.btn_OK.setOnClickListener {
            Utils.audioClick(context!!)
            builder.dismiss()
        }
    }

    private fun initPercent() {
        binding.btnPercent50.setImageResource(R.drawable.bg_faile_percent50)
        val listAns = listOf(binding.txtAnsA, binding.txtAnsB, binding.txtAnsC, binding.txtAnsD)
        var hidAns = 2
        do {
            val num = rd.nextInt(4)
            val ans = listAns[num]
            if (ans != trueCase.btn_true && ans.visibility == View.VISIBLE) {
                ans.visibility = View.INVISIBLE
                hidAns--
            }
        } while (hidAns > 0)
    }

    private fun initChanges() {
        binding.btnRestart.setImageResource(R.drawable.bg_faile_restart)
        val database = GetQuestionSQL(this@PlayFragment.context!!)
        itemQuestion = database.changeTheQuestion(levelQuestion)
        binding.item = itemQuestion
        inits()
    }

    private fun initStop() {
        lastMoney = lastMoney.trim().substring(2)
        binding.btnTerminate.setImageResource(R.drawable.bg_faile_terminate)
        if (checkLevel(levelQuestion -1)) {
            initsTimePlay().cancel(true)
            isPlay = false
            timePlay = 0
            initDialogSave(lastMoney)
        } else {
            initDialogTerminal(lastMoney)
        }
    }


    private fun getHelpPercent(trueCa: Int, level: Int): List<Float> {
        val rd = Random()
        var arrCase = intArrayOf(0, 0, 0, 0)
        var percentTrue = intArrayOf(0, 0, 0, 0)
        var tile = 25
        var tang = 0
        var khangia = 100
        if (level in 1..5) {
            tang = 30
        } else if (level in 6..9) {
            tang = 20
        } else if (level in 10..13) {
            tang = 10
        } else {
            tang = 0
        }
        for (i in arrCase.indices) {
            arrCase[i] += tile
            if (i == (trueCa - 1)) {
                arrCase[i] += tang
                tile += tang
            }
            tile += 25
        }
        var tong = 100 + tang
        for (i in 0 until khangia) {
            var rdTrue = rd.nextInt(tong)
            if (0 <= rdTrue && rdTrue < arrCase[0]) {
                percentTrue[0]++
            } else if (rdTrue >= arrCase[0] && rdTrue < arrCase[1]) {
                percentTrue[1]++
            } else if (rdTrue >= arrCase[1] && rdTrue < arrCase[2]) {
                percentTrue[2]++
            } else {
                percentTrue[3]++
            }
        }
        return listOf(
            (percentTrue[0] * 100.0f) / khangia, (percentTrue[1] * 100.0f) / khangia,
            (percentTrue[2] * 100.0f) / khangia, (percentTrue[3] * 100.0f) / khangia
        )
    }


    private fun initDialogNotiHelp() {
        val dialogView =
            LayoutInflater.from(this@PlayFragment.context)
                .inflate(R.layout.dialog_notification_help, null)
        val builder = AlertDialog.Builder(this@PlayFragment.context).setView(dialogView).show()
        builder.btn_Ok.setOnClickListener {
            Utils.audioClick(context!!)
            builder.dismiss()
        }
    }

    private fun initIconBTN() {
        if (statusHelp.stCall) {
            binding.btnCall.setImageResource(R.drawable.bg_faile_call)
        } else {
            binding.btnCall.setImageResource(R.drawable.call)
        }
        if (statusHelp.stHelp) {
            binding.btnHelp.setImageResource(R.drawable.bg_faile_help)
        } else {
            binding.btnHelp.setImageResource(R.drawable.help)
        }
        if (statusHelp.stPercent50) {
            binding.btnPercent50.setImageResource(R.drawable.bg_faile_percent50)
        } else {
            binding.btnPercent50.setImageResource(R.drawable.percent50)
        }
        if (statusHelp.stReset) {
            binding.btnRestart.setImageResource(R.drawable.bg_faile_restart)
        } else {
            binding.btnRestart.setImageResource(R.drawable.restart)
        }
    }


    private fun trueCase(): TrueCase {
        var btn_true: TextView? = null
        when (itemQuestion.trueCase.trim()) {
            "1" -> {
                btn_true = binding.txtAnsA
                trueCase = TrueCase(btn_true, "lose_a")
            }
            "2" -> {
                btn_true = binding.txtAnsB
                trueCase = TrueCase(btn_true, "lose_b")
            }
            "3" -> {
                btn_true = binding.txtAnsC
                trueCase = TrueCase(btn_true, "lose_c")
            }
            "4" -> {
                btn_true = binding.txtAnsD
                trueCase = TrueCase(btn_true, "lose_d")
            }
        }
        return trueCase
    }

    private fun checkAns(btn: TextView, audioAns: String, audioTrue: String) {
        isPlay = false
        initsTimePlay().cancel(true)
        Utils.audioClick(context!!)
        disableButton()
        btn.setBackgroundResource(R.drawable.bg_choose)
        val animation: AnimationDrawable = btn.background as AnimationDrawable
        animation.start()
        media = mediaPlayer.setUri(context!!, audioAns)
        mediaPlayer.start()
        media?.setOnCompletionListener {
            if (check(btn, trueCase.btn_true)) {
                mediaPlayer.release()
                media = mediaPlayer.setUri(context!!, audioTrue)
                mediaPlayer.start()
                media?.setOnCompletionListener {
                    if (levelQuestion == 15) {
                        val mon = checkMoney(levelQuestion)
                        initDialogSave(mon)
                    } else {
                        (activity as ContainActivity).backQuestionFragment(
                            itemQuestion.level + 1,
                            statusHelp,
                            listQuestion
                        )
                    }
                }
            } else {
                media = mediaPlayer.setUri(context!!, trueCase.nameAudio)
                mediaPlayer.start()
                media?.setOnCompletionListener {
                    val mon = checkMoney(levelQuestion-1)
                    if (checkLevel(levelQuestion-1)) {
                        initDialogSave(mon)
                    } else {
                        initDialogTerminal(mon)

                    }
                }
            }
        }
    }

    private fun disableButton() {
        binding.txtAnsB.isEnabled = false
        binding.txtAnsC.isEnabled = false
        binding.txtAnsA.isEnabled = false
        binding.txtAnsD.isEnabled = false
        binding.btnRestart.isEnabled = false
        binding.btnPercent50.isEnabled = false
        binding.btnHelp.isEnabled = false
        binding.btnTerminate.isEnabled = false
        binding.btnCall.isEnabled = false
    }

    private fun saveUser(userName: String, money: String, currentLevel: Int): Boolean {
        val data = context!!.getSharedPreferences("appTP", Context.MODE_PRIVATE)
        val level5 = data.getInt("Level5", 9)
        val level4 = data.getInt("Level4", 7)
        val level3 = data.getInt("Level3", 5)
        val level2 = data.getInt("Level2", 3)
        val level1 = data.getInt("Level1", 1)
        val edt = data.edit()
        when {
            currentLevel in level1 until level2 -> {
                edt.putString("Name1", userName)
                edt.putString("Money1", money)
                edt.putInt("Level1", currentLevel)
                edt.apply()
                return true
            }
            currentLevel in level2 until level3 -> {
                edt.putString("Name2", userName)
                edt.putString("Money2", money)
                edt.putInt("Level2", currentLevel)
                edt.apply()
                return true
            }
            currentLevel in level3 until level4 -> {
                edt.putString("Name3", userName)
                edt.putString("Money3", money)
                edt.putInt("Level3", currentLevel)
                edt.apply()
                return true
            }
            currentLevel in level3 until level4 -> {
                edt.putString("Name4", userName)
                edt.putString("Money4", money)
                edt.putInt("Level4", currentLevel)
                edt.apply()
                return true
            }
            currentLevel >= level5 -> {
                edt.putString("Name5", userName)
                edt.putString("Money5", money)
                edt.putInt("Level5", currentLevel)
                edt.apply()
                return true
            }
            else -> return false
        }

    }

    private fun checkLevel(level: Int): Boolean {
        val data = context!!.getSharedPreferences("appTP", Context.MODE_PRIVATE)
        val levelMax = data.getInt("Level1", 0)
        if (level >= levelMax) {
            return true
        }
        return false
    }


    private fun check(btn: TextView, btn_true: TextView): Boolean {
        return if (btn == btn_true) {
            btn.setBackgroundResource(R.drawable.bg_true)
            val animation: AnimationDrawable = btn.background as AnimationDrawable
            animation.start()
            true
        } else {
            btn.setBackgroundResource(R.drawable.bg_faile)
            val anim: AnimationDrawable = btn.background as AnimationDrawable
            anim.start()
            btn_true.setBackgroundResource(R.drawable.bg_true)
            val animation: AnimationDrawable = btn_true.background as AnimationDrawable
            animation.start()
            false
        }
    }

    private fun checkMoney(level: Int): String {
        return if (level in 1..4) {
            "0 VND"
        } else if (level in 5..9) {
            bonus.bonus_lv5 + " VND"
        } else {
            bonus.bonus_lv10 + " VND"
        }
    }

    private fun initDialogTerminal(money: String) {
        val dialogView =
            LayoutInflater.from(this@PlayFragment.context).inflate(R.layout.dialog_terminal, null)
        val builder = AlertDialog.Builder(this@PlayFragment.context).setView(dialogView).show()
        builder.txt_Money.text = money
        timePlay = 0
        initsTimePlay().cancel(true)
        builder.btn_Ok.setOnClickListener {
            Utils.audioClick(context!!)
            builder.dismiss()
            mediaPlayer.stop()
            mediaBg.stop()
            (activity as ContainActivity).mainFragment()
        }
    }

    private fun initDialogSave(money: String) {
        var name: String? = null
        val dialogView =
            LayoutInflater.from(this@PlayFragment.context).inflate(R.layout.dialog_save_user, null)
        val builder = AlertDialog.Builder(this@PlayFragment.context).setView(dialogView).show()
        builder.txt_Money.text = money
        builder.btn_Save.setOnClickListener {
            Utils.audioClick(context!!)
            name = builder.edt_Username.text.toString()
            name = if (name!!.trim() == "") {
                "NoName"
            } else {
                builder.edt_Username.text.toString()
            }
            saveUser(name!!, money, levelQuestion-1)
            builder.dismiss()
            (activity as ContainActivity).mainFragment()
        }
        builder.btn_Cancel.setOnClickListener {
            Utils.audioClick(context!!)
            builder.dismiss()
            (activity as ContainActivity).mainFragment()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class initsTimePlay : AsyncTask<Void, Int, Int>() {
        override fun doInBackground(vararg params: Void?): Int {
            try {
                while (timePlay > 0 && isPlay) {
                    if (isCancelled) {
                        break
                    }
                    timePlay--
                    publishProgress(timePlay)
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return 0
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val time = values[0]
            initTimeUI(time!!)
            if (time == 0) {
                mediaPlayer.setUri(context!!, "out_of_time")
                mediaPlayer.start()
                val mon = checkMoney(levelQuestion-1)
                initDialogTerminal(mon)
            }
        }
    }

    private fun initTimeUI(currTime: Int) {

        binding.timeProcess.progressMax = 30F
        binding.timeProcess.progress = currTime * 1F
        binding.txtCurrentTime.text = "$currTime s"
    }

    override fun onStop() {
        if (mediaBg.isPlaying) {
            mediaBg.stop()
        }
        isPlay = false
        initsTimePlay().cancel(true)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        isPlay = true
        mediaBg.start()
        initsTimePlay().execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaBg.isPlaying) {
            mediaBg.stop()
        }
        mediaBg.release()
        media?.release()
        mediaPlayer.release()
    }

    override fun onBackPressed(): Boolean {
        return if (mediaBg.isPlaying) {
            Toast.makeText(this@PlayFragment.context, "Can't Back...", Toast.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

    private fun playAudioQuestion15() {
        val media = MediaManager()
        val m = media.setUri(context!!, "important")
        media.start()
        m.setOnCompletionListener {
            media.release()
        }

    }
}