package com.xinzy.zhihu.daily.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.main.model.Story
import com.xinzy.zhihu.daily.util.EVENT_ITEM_CLICK

/**
 * Created by Xinzy on 2018/3/23.
 */

class StoryProvider : MultiAdapter.ItemProvider<Story>() {

    override fun onBindViewHolder(viewHolder: MultiAdapter.MultiViewHolder?, data: Story?, position: Int) {
        val titleText = viewHolder!!.get<TextView>(R.id.itemStoryTitleText)
        val imageView = viewHolder.get<ImageView>(R.id.itemStoryImage)

        imageView.visibility = if (data!!.hasImage()) View.VISIBLE else View.GONE
        if (data.hasImage()) Glide.with(imageView.context).load(data.img()).placeholder(R.drawable.placeholder).into(imageView)
        titleText.text = data.title

        viewHolder.rootView().setOnClickListener({v ->  onEvent(v, EVENT_ITEM_CLICK, position, data)})
    }

    override fun getItemViewLayoutId() = R.layout.item_story

}