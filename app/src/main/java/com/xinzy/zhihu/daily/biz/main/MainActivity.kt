package com.xinzy.zhihu.daily.biz.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.xinzy.zhihu.daily.R
import com.xinzy.zhihu.daily.base.BaseActivity
import com.xinzy.zhihu.daily.biz.main.contract.MainContract
import com.xinzy.zhihu.daily.biz.main.fragment.IndexFragment
import com.xinzy.zhihu.daily.biz.main.fragment.ThemeFragment
import com.xinzy.zhihu.daily.biz.main.model.Theme
import com.xinzy.zhihu.daily.biz.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

const val NAV_MENU_GROUP_ID = 1
const val NAV_MENU_ID = 100

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, mainDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mainNavigationView.setNavigationItemSelectedListener(this)

        mPresenter = MainPresenter(this)
        mPresenter.loadTheme()

        val indexFragment = IndexFragment()
        supportFragmentManager.beginTransaction().add(R.id.mainContainer, indexFragment, "index-0").commit()
    }

    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val themeId = item.itemId - NAV_MENU_ID
        val theme = mPresenter.getThemeById(themeId)
        supportActionBar!!.title = theme?.name

        val fragment = supportFragmentManager.findFragmentByTag("index-$themeId")
        if (fragment != null) {
            supportFragmentManager.fragments.forEach {
                val transaction = supportFragmentManager.beginTransaction()
                if (it == fragment) transaction.show(it)
                else transaction.hide(it)
                transaction.commit()
            }
        } else {
            if (theme != null) {
                supportFragmentManager.beginTransaction().add(R.id.mainContainer, ThemeFragment.newInstance(theme), "index-$themeId").commit()
            }
        }

        mainDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showTheme(themes: List<Theme>) {
        mainNavigationView.menu.removeGroup(NAV_MENU_GROUP_ID)
        themes.forEach({it -> mainNavigationView.menu.add(NAV_MENU_GROUP_ID, NAV_MENU_ID + it.id, 0, it.name)})
        mainNavigationView.menu.setGroupCheckable(NAV_MENU_GROUP_ID, true, true)
        mainNavigationView.menu.getItem(0).isChecked = true
    }
}
