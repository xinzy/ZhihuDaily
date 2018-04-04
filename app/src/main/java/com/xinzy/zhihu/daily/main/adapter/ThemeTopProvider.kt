package com.xinzy.zhihu.daily.main.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.main.model.ThemeTop
import com.xinzy.zhihu.daily.util.EVENT_THEME_TOP_CLICK

/**
 * Created by Xinzy on 2018/3/28.
 */

class ThemeTopProvider : MultiAdapter.ItemProvider<ThemeTop>() {

    override fun onBindViewHolder(viewHolder: MultiAdapter.MultiViewHolder?, data: ThemeTop?, position: Int) {
        val text = viewHolder!!.get<TextView>(R.id.themeDescText)
        val image = viewHolder.get<ImageView>(R.id.themeImage)

        text.text = data!!.mDesc
        Glide.with(image.context).load(data.mImageUrl).placeholder(R.drawable.placeholder).into(image)

        viewHolder.rootView().setOnClickListener { onEvent(image, EVENT_THEME_TOP_CLICK, position, data.mImageUrl) }
    }

    override fun getItemViewLayoutId() = R.layout.item_theme_top
}