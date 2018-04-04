package com.xinzy.zhihu.daily.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.main.model.Top
import com.xinzy.zhihu.daily.util.EVENT_CAROUSEL_ITEM_CLICK
import com.xinzy.zhihu.daily.widget.BannerView

/**
 * Created by Xinzy on 2018/3/26.
 */
class BannerProvider : MultiAdapter.ItemProvider<Top>() {

    override fun onBindViewHolder(viewHolder: MultiAdapter.MultiViewHolder?, data: Top?, position: Int) {
        val banner = viewHolder!!.get<BannerView>(R.id.itemCarousel)
        banner.setAdapter(object : BannerView.Adapter {
            override fun getCount() = data!!.mTopStories.size

            override fun convert(view: View, position: Int) {
                val img = view.findViewById<ImageView>(R.id.itemImage)
                val text = view.findViewById<TextView>(R.id.itemTitle)

                val item = data!!.mTopStories[position]
                text.text = item.title
                Glide.with(view.context).load(item.img()).placeholder(R.drawable.placeholder).into(img)
            }

            override fun getLayout() = R.layout.item_roll
        })

        banner.setOnItemClickListener(object : BannerView.OnItemClickListener {
            override fun onItemClick(view: BannerView, p: Int) {
                val item = data!!.mTopStories[p]
                onEvent(banner, EVENT_CAROUSEL_ITEM_CLICK, position, item)
            }
        })
    }

    override fun getItemViewLayoutId() = R.layout.item_carousel
}