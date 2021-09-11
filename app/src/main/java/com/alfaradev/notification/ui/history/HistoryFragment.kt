package com.alfaradev.notification.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfaradev.notification.R
import com.alfaradev.notification.models.History
import com.alfaradev.notification.utils.ext.gone
import com.alfaradev.notification.utils.ext.showToast
import com.alfaradev.notification.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val historyViewModel : HistoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observe()
    }

    private fun setUpRecyclerView() {
        recycler_view.apply {
            adapter = HistoryAdapter(mutableListOf(), requireActivity())
            layoutManager = LinearLayoutManager(requireActivity())

        }
    }

    private fun observe() {
        observeState()
        observeHistories()
    }

    private fun observeState() = historyViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUiState(it) })
    private fun observeHistories() = historyViewModel.listenToHistories().observe(viewLifecycleOwner, Observer { handleHistories(it) })

    private fun handleHistories(list: List<History>?) {
        list?.let {
            recycler_view.adapter?.let { adapter ->
                if (adapter is HistoryAdapter) adapter.updateList(it)
            }
        }
    }

    private fun handleUiState(homeState: HomeState?) {
        homeState?.let {
            when(it){
                is HomeState.Loading -> handleLoading(it.state)
                is HomeState.ShowToast -> requireActivity().showToast(it.message)
            }
        }
    }

    private fun handleLoading(state: Boolean) {
        if (state) loading.visible() else loading.gone()
    }

    private fun getHistories() = historyViewModel.getHistories()

    override fun onResume() {
        super.onResume()
        getHistories()
    }

}