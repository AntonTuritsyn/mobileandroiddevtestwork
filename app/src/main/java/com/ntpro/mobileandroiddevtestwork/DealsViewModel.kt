package com.ntpro.mobileandroiddevtestwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

private const val DEALS_PER_PAGE = 1000

class DealsViewModel(
    pagingSource: DealsPagingSource
): ViewModel() {
    //      передача данных в поток
    val items = Pager(
        config = PagingConfig(DEALS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { pagingSource }
    )
        .flow
        .cachedIn(viewModelScope)
}