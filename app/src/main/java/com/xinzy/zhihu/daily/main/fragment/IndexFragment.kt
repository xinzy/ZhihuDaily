package com.xinzy.zhihu.daily.main.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.base.BaseFragment
import com.xinzy.zhihu.daily.detail.DetailActivity
import com.xinzy.zhihu.daily.main.adapter.StoryProvider
import com.xinzy.zhihu.daily.main.adapter.TitleProvider
import com.xinzy.zhihu.daily.main.adapter.BannerProvider
import com.xinzy.zhihu.daily.main.contract.IndexContract
import com.xinzy.zhihu.daily.main.model.Story
import com.xinzy.zhihu.daily.main.presenter.IndexPresenter
import com.xinzy.zhihu.daily.util.*
import kotlinx.android.synthetic.main.fragment_index.*

/**
 * Created by Xinzy on 2018/3/22.
 */
class IndexFragment : BaseFragment(), IndexContract.View, MultiAdapter.OnEventListener {
    private lateinit var mAdapter: MultiAdapter
    private lateinit var mPresenter: IndexContract.Presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = IndexPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_index, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mAdapter = MultiAdapter()
        mAdapter.registerProvider(VIEW_TYPE_TOP_STORIES, BannerProvider()).registerProvider(VIEW_TYPE_TITLE, TitleProvider()).registerProvider(VIEW_TYPE_STORY, StoryProvider())
        mAdapter.setOnEventListener(this)
        indexRecyclerView.layoutManager = LinearLayoutManager(context)
        indexRecyclerView.adapter = mAdapter


        indexRefreshLayout.setOnRefreshListener { mPresenter.reload() }
        indexRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView!!.canScrollVertically(1)) mPresenter.load()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.load()
    }

    override fun onEvent(view: View?, event: Short, position: Int, vararg param: Any?) {
        when (event) {
            EVENT_ITEM_CLICK,
            EVENT_CAROUSEL_ITEM_CLICK -> {
                val story = if (param.isNotEmpty()) param[0] else null
                story?.let {
                    if (story is Story) DetailActivity.start(context, story)
                }
            }
        }
    }

    override fun showLoading() {
        indexRefreshLayout.isRefreshing = true
    }

    override fun closeLoading() {
        indexRefreshLayout.isRefreshing = false
    }

    override fun show(list: List<MultiAdapter.ViewType>, isAdd: Boolean) {
        if (isAdd) {
            mAdapter.addAll(list)
        } else {
            mAdapter.replace(list)
        }
    }

}