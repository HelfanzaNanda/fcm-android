package com.alfaradev.notification.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alfaradev.notification.R
import com.alfaradev.notification.utils.Constants
import com.alfaradev.notification.utils.ext.gone
import com.alfaradev.notification.utils.ext.showToast
import com.alfaradev.notification.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel : HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        sendNotification()
    }

    private fun observe() {
        observeState()
    }

    private fun observeState() = homeViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUiState(it) })

    private fun handleUiState(homeState: HomeState?) {
        homeState?.let {
            when(it){
                is HomeState.Loading -> handleLoading(it.state)
                is HomeState.ShowToast -> requireActivity().showToast(it.message)
                is HomeState.SuccessCreateToken -> handleSuccessCreateToken(it.token)
            }
        }
    }

    private fun handleSuccessCreateToken(token: String) {
        Constants.setToken(requireActivity(), token)
    }

    private fun handleLoading(b: Boolean) {
        if (b) loading.visible() else loading.gone()
        btn_send_notif.isEnabled = !b
    }

    private fun createToken() = homeViewModel.generateTokenFirebase()


    private fun sendNotification(){
        btn_send_notif.setOnClickListener {
            homeViewModel.sendNotification(Constants.getToken(requireActivity()))
        }
    }

    override fun onResume() {
        super.onResume()
        createToken()
    }
}