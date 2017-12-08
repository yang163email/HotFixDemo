package com.yan.tinker

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/8 15:24
 *  @description ：首页
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_version.text = "${BuildConfig.VERSION_NAME}.${BuildConfig.VERSION_CODE}"
        tv_content.text = "bug第二次修复啦"
        tv_content.setTextColor(Color.RED)
    }
}
