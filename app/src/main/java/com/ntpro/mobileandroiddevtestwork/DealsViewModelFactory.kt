package com.ntpro.mobileandroiddevtestwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DealsViewModelFactory(
    private val pagingSource: DealsPagingSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DealsViewModel(pagingSource) as T
    }
}