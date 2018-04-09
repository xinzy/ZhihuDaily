package com.xinzy.zhihu.daily.biz.detail

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Keep
import android.view.MenuItem
import android.webkit.*
import com.bumptech.glide.Glide
import com.xinzy.essence.kotlin.util.L
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.base.BaseActivity
import com.xinzy.zhihu.daily.biz.detail.constact.DetailConstact
import com.xinzy.zhihu.daily.biz.detail.model.Detail
import com.xinzy.zhihu.daily.biz.detail.presenter.DetailPresenter
import com.xinzy.zhihu.daily.biz.image.ImageActivity
import com.xinzy.zhihu.daily.biz.main.model.Story
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity(), DetailConstact.View {

    private var mStory: Story? = null

    private lateinit var mPresenter: DetailConstact.Presenter

    companion object {
        private val KEY_STORY = "STORY"

        fun start(context: Context, story: Story) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_STORY, story)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        mStory = intent.getParcelableExtra(KEY_STORY)
        if (mStory == null) {
            finish()
            return
        }
        supportActionBar!!.title = mStory!!.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        webView!!.addJavascriptInterface(JSInterface(), "android")
        webView.webChromeClient = ZhihuWebChromeClient()
        webView.webViewClient = ZhihuWebViewClient()
        webView.settings.javaScriptEnabled = true

        mPresenter = DetailPresenter(this)
        mPresenter.load(mStory!!.id)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun show(detail: Detail) {
        Glide.with(this).load(detail.image).placeholder(R.drawable.placeholder).into(imageView)
        webView.loadData(detail.html(), "text/html", "UTF-8")
    }

    @Keep
    private inner class JSInterface {
        @JavascriptInterface
        fun showImage(src: String) {
            L.d("image click: $src")
            ImageActivity.start(this@DetailActivity, src)
        }
    }

    private inner class ZhihuWebChromeClient : WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            val message = consoleMessage!!.message()
            val lineNumber = consoleMessage.lineNumber()
            val sourceID = consoleMessage.sourceId()
            val messageLevel = consoleMessage.message()

            L.i(String.format("[%s] sourceID: %s lineNumber: %n message: %s", messageLevel, sourceID, lineNumber, message))
            return true
        }

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            AlertDialog.Builder(this@DetailActivity).setMessage(message).show()
            return true
        }
    }

    private inner class ZhihuWebViewClient : WebViewClient() {
    }
}
