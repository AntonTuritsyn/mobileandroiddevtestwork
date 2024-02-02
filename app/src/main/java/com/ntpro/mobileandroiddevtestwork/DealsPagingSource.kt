package com.ntpro.mobileandroiddevtestwork

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import kotlin.math.max

private const val STARTING_KEY = 0
private const val TAG = "PagingSource"
class DealsPagingSource : PagingSource<Int, Server.Deal>() {

    private val server = Server()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Server.Deal> {
//        настройка подгрузки списка
        val startKey = params.key ?: STARTING_KEY
        val range = startKey.until(startKey + params.loadSize)
        val prevKey = when (startKey) {
            STARTING_KEY -> null
            else -> ensureValidKey(key = range.first - params.loadSize)
        }
        val nextKey = range.last + 1
        var dealList: List<Server.Deal>? = null
        val callback: (List<Server.Deal>) -> Unit = {list ->
//            сортировка по дате
            val filtered = list.sortedBy { it.timeStamp }
            dealList = filtered
        }
//
        delay(5)
        server.subscribeToDeals(callback)
//        задержка для получения первого колбэка, чтобы список не был пустым (NPE)
        while (dealList == null) {
            delay(1)
        }
        return LoadResult.Page(dealList!!, prevKey, nextKey)
    }

    override fun getRefreshKey(state: PagingState<Int, Server.Deal>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val deal = state.closestItemToPosition(anchorPos) ?: return null
        return ensureValidKey((deal.id - (state.config.pageSize / 2)).toInt())

    }
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

}
