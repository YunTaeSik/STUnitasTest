package com.example.stunitastest.presentation.ui.search

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stunitastest.BR
import com.example.stunitastest.R
import com.example.stunitastest.databinding.SearchBinding
import com.example.stunitastest.presentation.ui.base.BackDoubleClickFinishActivity
import com.example.stunitastest.presentation.viewmodel.SearchViewModel

class SearchActivity : BackDoubleClickFinishActivity<SearchBinding>() {
    override fun onLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun setupViewModel(): SparseArray<ViewModel> {
        val setupViewModel = SparseArray<ViewModel>()
        setupViewModel.put(BR.model, ViewModelProvider(this).get(SearchViewModel::class.java))
        return setupViewModel
    }

    override fun observer() {
    }
}