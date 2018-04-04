package com.xinzy.zhihu.daily.main.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xinzy.widget.MultiAdapter
import com.xinzy.widget.NestedGridView
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.common.glide.RoundCornerTransformation
import com.xinzy.zhihu.daily.main.model.Editor
import com.xinzy.zhihu.daily.main.model.Editors

/**
 * Created by Xinzy on 2018/3/28.
 */

class EditorsProvider : MultiAdapter.ItemProvider<Editors>() {
    override fun onBindViewHolder(viewHolder: MultiAdapter.MultiViewHolder?, data: Editors?, position: Int) {
        val gridView = viewHolder!!.get<NestedGridView>(R.id.gridView)
        if (data!!.editors.isNotEmpty()) {
            gridView.adapter = object : NestedGridView.NestedGridAdapter<Editor>(data.editors) {
                override fun getLayout() = R.layout.item_editor

                override fun onBindData(convertView: View?, item: Editor?, position: Int) {
                    val imageView = convertView!!.findViewById<ImageView>(R.id.image)
                    Glide.with(imageView.context).load(item!!.avatar).placeholder(R.drawable.placeholder)
                            .transform(RoundCornerTransformation(imageView.context)).into(imageView)
                }
            }
        }
    }

    override fun getItemViewLayoutId() = R.layout.item_editors

}