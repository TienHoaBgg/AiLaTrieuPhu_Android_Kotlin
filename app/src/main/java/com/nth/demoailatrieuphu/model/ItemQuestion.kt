package com.nth.demoailatrieuphu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemQuestion(val question:String,val level:Int,val ansA:String,val  ansB:String,val ansC:String,val ansD:String,val trueCase:String) :
    Parcelable