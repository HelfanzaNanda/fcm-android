package com.alfaradev.notification.ui.home


import androidx.lifecycle.ViewModel
import com.alfaradev.notification.models.User
import com.alfaradev.notification.repositories.FcmRepository
import com.alfaradev.notification.repositories.FirebaseRepository
import com.alfaradev.notification.utils.SingleLiveEvent
import com.alfaradev.notification.utils.SingleResponse

class HomeViewModel(private val fcmRepository: FcmRepository,
    private val firebaseRepository: FirebaseRepository) : ViewModel() {

    private val state : SingleLiveEvent<HomeState> = SingleLiveEvent()

    private fun isLoading(b : Boolean){ state.value = HomeState.Loading(b) }
    private fun toast(m : String){ state.value = HomeState.ShowToast(m) }
    private fun successCreateToken(token: String){ state.value = HomeState.SuccessCreateToken(token) }

    fun generateTokenFirebase(){
        isLoading(true)
        firebaseRepository.generateToken(object : SingleResponse<String> {
            override fun onSuccess(data: String?) {
                data?.let {fcm_token ->
                    createToken(fcm_token)
                }
            }

            override fun onFailure(err: Error) {
                isLoading(false)
                toast(err.message.toString())
            }

        })
    }

    private fun createToken(token: String){
        fcmRepository.createToken(token, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                data?.let {
                    isLoading(false)
                    successCreateToken(token)
                }
            }

            override fun onFailure(err: Error) {
                isLoading(false)
                toast(err.message.toString())
            }
        })
    }

    fun sendNotification(token: String){
        isLoading(true)
        fcmRepository.sendNotif(token, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                data?.let {
                    isLoading(false)
                    toast("berhasil mengirimkan notifikasi")
                }
            }

            override fun onFailure(err: Error) {
                isLoading(false)
                toast(err.message.toString())
            }
        })
    }

    fun listenToState() = state
}

sealed class HomeState {
    data class Loading(var state : Boolean) : HomeState()
    data class ShowToast(var message : String) : HomeState()
    data class SuccessCreateToken(var token : String) : HomeState()
}