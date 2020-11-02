package com.nth.demoailatrieuphu.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.model.ItemQuestion
import com.nth.demoailatrieuphu.model.StatusHelp


class ContainActivity : AppCompatActivity() {
    private lateinit var lawFragment: LawFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var questionFragment: QuestionFragment
    private lateinit var playFragment: PlayFragment
    private lateinit var lawWaitFragment: LawWaitFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment()

    }
     fun mainFragment() {
         clearBackStack()
         mainFragment = MainFragment()
         lawFragment = LawFragment()
         questionFragment = QuestionFragment()
         playFragment = PlayFragment()
         lawWaitFragment = LawWaitFragment()
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.content, mainFragment, MainFragment::class.java.name)
        frag.addToBackStack("mainFrag")
        frag.commit()
    }

    fun openLawFragment() {
        val tran = supportFragmentManager.beginTransaction()
        tran.setCustomAnimations(
            R.anim.anim_right_in,
            R.anim.anim_right_out,
            R.anim.anim_left_in,
            R.anim.anim_left_out
        )
            .add(R.id.content, lawFragment, LawFragment::class.java.name)
            .hide(supportFragmentManager.findFragmentByTag(MainFragment::class.java.name)!!)
            .addToBackStack("LawFragment")
            .commit()
    }

    fun openLawWaitFragment() {
        val tran = supportFragmentManager.beginTransaction()
        tran.setCustomAnimations(
            R.anim.anim_right_in,
            R.anim.anim_right_out,
            R.anim.anim_left_in,
            R.anim.anim_left_out
        )
            .add(R.id.content, lawWaitFragment, LawWaitFragment::class.java.name)
            .hide(supportFragmentManager.findFragmentByTag(LawFragment::class.java.name)!!)
            .addToBackStack("LawWaitFragment")
            .commit()
    }

    fun openQuestionFragment(level: Int,status: StatusHelp,list: ArrayList<ItemQuestion>) {
        val bundle = Bundle()
        bundle.putInt("LEVEL", level)
        bundle.putParcelable("HELP",status)
        bundle.putParcelableArrayList("LIST",list)
        questionFragment.arguments = bundle
        val tran = supportFragmentManager.beginTransaction()
        tran.setCustomAnimations(
            R.anim.anim_left_in,
            R.anim.anim_left_out,
            R.anim.anim_right_in,
            R.anim.anim_right_out
        )
            .add(R.id.content, questionFragment, QuestionFragment::class.java.name)
            .hide(supportFragmentManager.findFragmentByTag(LawWaitFragment::class.java.name)!!)
            .addToBackStack("QuestionFragment")
            .commit()
    }

    fun backQuestionFragment(level: Int,status: StatusHelp,list: ArrayList<ItemQuestion>) {
        val bundle = Bundle()
        bundle.putInt("LEVEL", level)
        bundle.putParcelable("HELP",status)
        bundle.putParcelableArrayList("LIST",list)
        questionFragment.arguments = bundle
        val tran = supportFragmentManager.beginTransaction()
        tran.setCustomAnimations(
            R.anim.anim_left_in,
            R.anim.anim_left_out,
            R.anim.anim_right_in,
            R.anim.anim_right_out
        )
            .replace(R.id.content, questionFragment)
            .commit()
    }

    fun backPlayFragment(lastMoney:String,money: String, level: Int,status:StatusHelp,list: ArrayList<ItemQuestion>) {
        val bundle = Bundle()
        bundle.putInt("LEVEL", level)
        bundle.putString("MONEY", money)
        bundle.putString("LAST_MONEY", lastMoney)
        bundle.putParcelable("HELP",status)
        bundle.putParcelableArrayList("LIST",list)
        playFragment.arguments = bundle
        val tran = supportFragmentManager.beginTransaction()
        tran.setCustomAnimations(
            R.anim.anim_right_in,
            R.anim.anim_right_out,
            R.anim.anim_left_in,
            R.anim.anim_left_out
        )
            .replace(R.id.content, playFragment)
            .commit()
    }




    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.content)
        if ((fragment as? IOnBackPressed)?.onBackPressed()?.not() == true) {
            super.onBackPressed()
        }
    }


    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }

     fun clearBackStack() {
        val manager: FragmentManager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first: FragmentManager.BackStackEntry = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

}
