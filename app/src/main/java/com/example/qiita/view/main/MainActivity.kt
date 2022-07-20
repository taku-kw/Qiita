package com.example.qiita.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.qiita.R
import com.example.qiita.constant.UrlConst.Companion.QIITA_HELP_URL
import com.example.qiita.constant.UrlConst.Companion.YAHOO_URL
import com.example.qiita.view.contents.ContentsFragment
import com.example.qiita.view.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.settingIcon -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.topPageFragment, SettingFragment())
                    .addToBackStack(null)
                    .commit()

                true
            }

            R.id.helpIcon -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.topPageFragment, ContentsFragment.newInstance(QIITA_HELP_URL))
                    .addToBackStack(null)
                    .commit()

                true
            }

            R.id.searchIcon -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.topPageFragment, ContentsFragment.newInstance(YAHOO_URL))
                    .addToBackStack(null)
                    .commit()

                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}