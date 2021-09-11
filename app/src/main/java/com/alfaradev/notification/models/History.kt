package com.alfaradev.notification.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class History(
    @SerializedName("fcm_token") var fcm_token : String? = null,
    @SerializedName("body") var body : String? = null
) : Parcelable