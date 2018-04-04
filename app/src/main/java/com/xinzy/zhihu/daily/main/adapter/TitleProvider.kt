package com.xinzy.zhihu.daily.main.adapter

import android.widget.TextView
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.main.model.Title

/**
 * Created by Xinzy on 2018/3/23.
 */

class TitleProvider : MultiAdapter.ItemProvider<Title>() {

    override fun onBindViewHolder(viewHolder: MultiAdapter.MultiViewHolder?, data: Title?, position: Int) {
        viewHolder?.get<TextView>(R.id.itemTitleText)?.text = data!!.mTitle
    }

    override fun getItemViewLayoutId() = R.layout.item_title
}
