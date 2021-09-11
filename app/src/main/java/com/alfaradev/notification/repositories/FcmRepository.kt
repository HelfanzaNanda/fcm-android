package com.alfaradev.notification.repositories

import com.alfaradev.notification.models.User
import com.alfaradev.notification.utils.SingleResponse
import com.alfaradev.notification.webservices.ApiService
import com.alfaradev.notification.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface FcmContract{
    fun createToken(token : String, listener : SingleResponse<User>)
    fun sendNotif(token: String, listener: SingleResponse<User>)
}

class FcmRepository(private val api : ApiService) : FcmContract{
    override fun createToken(token: String, listener: SingleResponse<User>) {
        api.createToken(token).enqueue(object : Callback<WrappedResponse<User>> {
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(call: Call<WrappedResponse<User>>,
                                    response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }

        })
    }

    override fun sendNotif(token: String, listener: SingleResponse<User>) {
        api.sendNotif(token).enqueue(object : Callback<WrappedResponse<User>> {
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(call: Call<WrappedResponse<User>>,
                                    response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }

        })
    }
}