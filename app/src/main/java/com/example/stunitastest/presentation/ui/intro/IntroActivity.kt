package com.example.stunitastest.presentation.ui.intro

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stunitastest.BR
import com.example.stunitastest.R
import com.example.stunitastest.databinding.IntroBinding
import com.example.stunitastest.presentation.base.BaseActivity
import com.example.stunitastest.presentation.ui.search.SearchActivity
import io.reactivex.Single
import java.util.concurrent.TimeUnit


class IntroActivity : BaseActivity<IntroBinding>() {


    override fun onLayoutId(): Int {
        return R.layout.activity_intro
    }

    override fun setupViewModel(): SparseArray<ViewModel> {
        val setupViewModel = SparseArray<ViewModel>()
        setupViewModel.put(BR.model, ViewModelProvider(this).get(IntroViewModel::class.java))
        return setupViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model?.addDisposable(Single.timer(2, TimeUnit.SECONDS).subscribe { _ ->
            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        })
    }


    override fun observer() {
    }
}
