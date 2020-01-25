package com.example.stunitastest.presentation

import android.os.Bundle
import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stunitastest.BR
import com.example.stunitastest.R
import com.example.stunitastest.databinding.MainViewBinding
import com.example.stunitastest.presentation.ui.base.BackDoubleClickFinishActivity
import com.example.stunitastest.presentation.viewmodel.MainViewModel

class MainActivity : BackDoubleClickFinishActivity<MainViewBinding>() {
    override fun onLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setupViewModel(): SparseArray<ViewModel> {
        val setupViewModel = SparseArray<ViewModel>()
        setupViewModel.put(BR.model, ViewModelProvider(this).get(MainViewModel::class.java))
        return setupViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun observer() {
    }

}
