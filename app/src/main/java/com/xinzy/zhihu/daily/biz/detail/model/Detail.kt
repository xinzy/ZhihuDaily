package com.xinzy.zhihu.daily.biz.detail.model

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Xinzy on 2018/3/27.
 */

@Keep
class Detail {
    companion object {
        val TEMPLATE = """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>%s</title>
                    %s
                    %s
					<script>
						window.onload = function() {
							var objs = document.getElementsByTagName("img");
							for(var i = 0; i < objs.length; i++) {
								objs[i].onclick = function() {
                                    console.warn("image click: " + this.src);
                                    android.showImage(this.src);
								}
							}
						}
					</script>
                </head>
                <body>%s</body>
            </html>
            """

        val CSS = "<link rel=\"stylesheet\" href=\"%1\$s\" />"
        val JS = "<script src=\"%1\$s\"></script>"
    }

    var id = 0
    var title = ""
    var body = ""
    var image = ""
    var images = arrayListOf<String>()

    var type = 0
    var js = arrayListOf<String>()
    var css = arrayListOf<String>()


    @SerializedName ("image_source")
    var imageSource = ""
    @SerializedName ("share_url")
    var shareUrl = ""


    fun html(): String {
        val jsString = StringBuffer()
        val cssString = StringBuffer()
        js.forEach { jsString.append(String.format(JS, it)).append('\n') }
        css.forEach { cssString.append(String.format(CSS, it)).append('\n') }

        return String.format(TEMPLATE, title, jsString.toString(), cssString.toString(), body).replace("<div class=\"img-place-holder\"></div>", "")
    }
}