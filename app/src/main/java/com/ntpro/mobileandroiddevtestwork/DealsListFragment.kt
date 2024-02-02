package com.ntpro.mobileandroiddevtestwork

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.ntpro.mobileandroiddevtestwork.databinding.FragmentTableTitleBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

private const val TAG = "DealsListFragment"

class DealsListFragment : Fragment(){

    private var _binding: FragmentTableTitleBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Ошибка"
        }

    private val dealsViewModel: DealsViewModel by viewModels {
        DealsViewModelFactory(pagingSource = DealsPagingSource())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTableTitleBinding.inflate(inflater, container, false)
        binding.dealsListRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DealsListAdapter(requireContext())
        binding.dealsListRecyclerView.adapter = adapter
        Log.d(TAG, "adapter: $adapter")
        viewLifecycleOwner.lifecycleScope.launch {
//            получение данных из ViewModel
            dealsViewModel.items.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}