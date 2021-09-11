package com.alfaradev.notification.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alfaradev.notification.models.History
import com.alfaradev.notification.models.User
import com.alfaradev.notification.repositories.FcmRepository
import com.alfaradev.notification.repositories.FirebaseRepository
import com.alfaradev.notification.repositories.HistoryRepository
import com.alfaradev.notification.utils.ArrayResponse
import com.alfaradev.notification.utils.SingleLiveEvent
import com.alfaradev.notification.utils.SingleResponse

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    private val state : SingleLiveEvent<HomeState> = SingleLiveEvent()
    private val histories = MutableLiveData<List<History>>()

    private fun isLoading(b : Boolean){ state.value = HomeState.Loading(b) }
    private fun toast(m : String){ state.value = HomeState.ShowToast(m) }

    fun getHistories(){
        historyRepository.getHistories(object : ArrayResponse<History>{
            override fun onSuccess(datas: List<History>?) {
                datas?.let {
                    isLoading(false)
                    histories.postValue(it)
                }
            }

            override fun onFailure(err: Error) {
                isLoading(false)
                toast(err.message.toString())
            }
        })
    }

    fun listenToState() = state
    fun listenToHistories() = histories
}

sealed class HomeState {
    data class Loading(var state : Boolean) : HomeState()
    data class ShowToast(var message : String) : HomeState()
}