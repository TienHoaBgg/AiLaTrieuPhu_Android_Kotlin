package com.nth.demoailatrieuphu.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusHelp(var stReset:Boolean,var stCall:Boolean,var stPercent50:Boolean,var stHelp:Boolean) : Parcelable