package com.alfaradev.notification.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("fcm_token") var fcm_token : String? = null
) : Parcelable