package com.xinzy.zhihu.daily.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.R.drawable.placeholder
import com.xinzy.zhihu.daily.R.id.photoView
import com.xinzy.zhihu.daily.R.id.toolbar
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity(), Runnable {

    private val mHandler = Handler()

    companion object {
        private val KEY_IMG_URL = "IMG_URL"
        fun start(context: Context, url: String) {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(KEY_IMG_URL, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "大图浏览"

        val imageUrl = intent.getStringExtra(KEY_IMG_URL)
        if (TextUtils.isEmpty(imageUrl)) {
            finish()
            return
        }
        Glide.with(this).load(imageUrl).placeholder(R.drawable.placeholder).into(photoView)

        mHandler.postDelayed(this, 2000)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun run() {
        ViewCompat.animate(appBarLayout).alpha(0f).withLayer().setDuration(300).start()
    }
}
