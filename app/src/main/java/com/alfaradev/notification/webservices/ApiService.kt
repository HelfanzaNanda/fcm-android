package com.alfaradev.notification.webservices


import com.alfaradev.notification.models.History
import com.alfaradev.notification.models.User
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("create-token")
    fun createToken(
        @Field("fcm_token") fcm_token : String
    ) : Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("send-notif")
    fun sendNotif(
        @Field("fcm_token") fcm_token : String
    ) : Call<WrappedResponse<User>>


    @GET("history")
    fun histories(
    ) : Call<WrappedListResponse<History>>
}

data class WrappedResponse<T>(
    @SerializedName("message") var message : String?,
    @SerializedName("status") var status : Boolean?,
    @SerializedName("data") var data : T?
)

data class WrappedListResponse<T>(
    @SerializedName("message") var message : String?,
    @SerializedName("status") var status : Boolean?,
    @SerializedName("data") var data : List<T>?
)