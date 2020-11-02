package com.nth.demoailatrieuphu.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nth.demoailatrieuphu.R
import com.nth.demoailatrieuphu.Utils.Utils
import com.nth.demoailatrieuphu.adapter.MemberCallAdapter
import com.nth.demoailatrieuphu.databinding.DialogCallBinding
import com.nth.demoailatrieuphu.model.ItemCall

class DialogCallFragment :DialogFragment, MemberCallAdapter.IMember {
    private var ans:String
    private lateinit var binding:DialogCallBinding
    private lateinit var listMember:MutableList<ItemCall>
    constructor(ans:String){
        this.ans = ans
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCallBinding.inflate(LayoutInflater.from(context),container,false)
        binding.listMemberCall.layoutManager = GridLayoutManager(context, 3)
        binding.listMemberCall.adapter = MemberCallAdapter(this)
        listMember = mutableListOf()
        listMember.add(ItemCall("Leymar",R.drawable.leymar))
        listMember.add(ItemCall("Messi",R.drawable.messi))
        listMember.add(ItemCall("Công Vinh",R.drawable.congvinh))
        listMember.add(ItemCall("Cristiano Ronaldo",R.drawable.ronaldo))
        listMember.add(ItemCall("Luis Suárez",R.drawable.suarez))
        listMember.add(ItemCall("Bill Gates",R.drawable.bill))
        binding.btnOk.setOnClickListener {
            Utils.audioClick(context!!)
            this.dismiss()
        }
        return binding.root
    }

    override fun onClickItem(position: Int, v: View) {
        binding.txtCall.text = listMember[position].name +" "+ ans
        binding.txtCall.visibility = View.VISIBLE
    }

    override fun getItem(position: Int): ItemCall {
        return listMember[position]
    }

    override fun size(): Int {
        return  listMember.size
    }

}