package com.xinzy.zhihu.daily.base

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

/**
 * Created by Xinzy on 2018/3/22.
 */


fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun Context.toast(id: Int) = toast(getString(id))

fun Context.dp2px(dp: Float) = (resources.displayMetrics.density * dp + .5f).toInt()

fun View.dp2px(dp: Float) = context.dp2px(dp)

fun View.snack(msg: String) = Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()

fun View.snack(id: Int) = snack(resources.getString(id))


