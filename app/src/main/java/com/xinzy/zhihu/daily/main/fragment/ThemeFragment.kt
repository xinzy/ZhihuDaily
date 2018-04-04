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
import com.xinzy.zhihu.daily.image.ImageActivity
import com.xinzy.zhihu.daily.main.adapter.EditorsProvider
import com.xinzy.zhihu.daily.main.adapter.StoryProvider
import com.xinzy.zhihu.daily.main.adapter.ThemeTopProvider
import com.xinzy.zhihu.daily.main.contract.ThemeConstract
import com.xinzy.zhihu.daily.main.model.*
import com.xinzy.zhihu.daily.main.presenter.ThemePresenter
import com.xinzy.zhihu.daily.util.*
import kotlinx.android.synthetic.main.fragment_theme.*

/**
 * Created by Xinzy on 2018/3/28.
 */
class ThemeFragment : BaseFragment(), ThemeConstract.View, MultiAdapter.OnEventListener {

    private lateinit var mAdapter: MultiAdapter

    private lateinit var mTheme: Theme
    private lateinit var mPresenter: ThemeConstract.Presenter

    companion object {
        private val KEY_THEME = "THEME"

        fun newInstance(theme: Theme): ThemeFragment {
            val fragment = ThemeFragment()
            val args = Bundle()
            args.putParcelable(KEY_THEME, theme)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mTheme = arguments.getParcelable(KEY_THEME)
        mPresenter = ThemePresenter(this, mTheme.id)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_theme, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        themeRefreshLayout.setOnRefreshListener { mPresenter.reload() }
        themeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView!!.canScrollVertically(1)) mPresenter.load()
            }
        })

        mAdapter = MultiAdapter().registerProvider(VIEW_TYPE_STORY, StoryProvider())
                .registerProvider(VIEW_TYPE_THEME_TOP, ThemeTopProvider())
                .registerProvider(VIEW_TYPE_EDITOR, EditorsProvider())
        mAdapter.setOnEventListener(this)
        themeRecyclerView.layoutManager = LinearLayoutManager(context)
        themeRecyclerView.adapter = mAdapter

        mAdapter.add(ThemeTop(mTheme.description, mTheme.thumbnail))
        mPresenter.load()
    }

    override fun onEvent(view: View?, event: Short, position: Int, vararg param: Any?) {
        when (event) {
            EVENT_ITEM_CLICK -> {
                val story = if (param.isNotEmpty()) param[0] as Story else null
                story?.let { DetailActivity.start(context, story) }
            }
            EVENT_THEME_TOP_CLICK -> {
                val img = if (param.isNotEmpty()) param[0] as String else null
                img?.let { ImageActivity.start(context, img) }
            }
        }
    }

    override fun showLoading() {
        themeRefreshLayout.isRefreshing = true
    }

    override fun closeLoading() {
        themeRefreshLayout.isRefreshing = false
    }

    override fun showList(list: List<MultiAdapter.ViewType>, isAdd: Boolean) {
        if (isAdd) {
            mAdapter.addAll(list)
        } else {
            mAdapter.replace(list)
        }
    }


}