package com.example.stunitastest.presentation.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stunitastest.BR
import com.example.stunitastest.R
import com.example.stunitastest.databinding.SearchBinding
import com.example.stunitastest.extension.log
import com.example.stunitastest.extension.makeToast
import com.example.stunitastest.presentation.ui.base.BackDoubleClickFinishActivity
import com.example.stunitastest.presentation.viewmodel.SearchViewModel
import com.example.stunitastest.utils.EndlessRecyclerOnScrollListener
import com.example.stunitastest.utils.LinearLayoutManagerWrapper
import com.yts.baseproject.extension.showLoading
import com.yts.baseproject.extension.visible
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BackDoubleClickFinishActivity<SearchBinding>(), View.OnClickListener {
    private var mSearchAdapter: SearchAdapter? = null

    override fun onLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun setupViewModel(): SparseArray<ViewModel> {
        val setupViewModel = SparseArray<ViewModel>()
        setupViewModel.put(BR.model, ViewModelProvider(this).get(SearchViewModel::class.java))
        return setupViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        btn_text_delete.setOnClickListener(this)

        list_search.layoutManager = LinearLayoutManagerWrapper(this, RecyclerView.VERTICAL, false)
        mSearchAdapter = SearchAdapter()
        list_search.adapter = mSearchAdapter


        list_search.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(list_search.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                binding.model?.setPage(page)
                binding.model?.getImages()
            }

        })

    }

    override fun observer() {
        binding.model?.isLoading?.observe(this, Observer {
            loading.showLoading(it)
        })
        binding.model?.toastMessage?.observe(this, Observer {
            makeToast(it)
        })

        edit_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                btn_text_delete.visible(s != null && s.isNotEmpty())

                binding.model?.search(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.model?.listDocument?.observe(this, Observer {

            mSearchAdapter?.submitList(it)
            mSearchAdapter?.notifyDataSetChanged()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_text_delete -> {
                edit_search.setText("")
            }
        }
    }
}