package com.alfaradev.notification.repositories

import com.alfaradev.notification.models.History
import com.alfaradev.notification.utils.ArrayResponse
import com.alfaradev.notification.webservices.ApiService
import com.alfaradev.notification.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface HistoryContract{
    fun getHistories(listener : ArrayResponse<History>)
}

class HistoryRepository(private val api : ApiService) : HistoryContract {
    override fun getHistories(listener: ArrayResponse<History>) {
        api.histories().enqueue(object : Callback<WrappedListResponse<History>> {
            override fun onFailure(call: Call<WrappedListResponse<History>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(call: Call<WrappedListResponse<History>>,
                                    response: Response<WrappedListResponse<History>>
            ) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }

        })
    }
}