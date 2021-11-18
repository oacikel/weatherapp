package com.oacikel.baseapp.core

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.oacikel.baseapp.R

abstract class BaseActivity : AppCompatActivity() {

    //region Views
    lateinit var flBaseContent: FrameLayout
    //endregion

    private var progressDialog: Dialog? = null


    public override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        super.setContentView(R.layout.activity_base)
        findViewByIds()
        /*if (BuildConfig.DEBUG)
            Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this));*/
    }

    private fun findViewByIds() {
        flBaseContent = findViewById<View>(R.id.ll_main_content) as FrameLayout
    }


    override fun setContentView(id: Int) {
        val inflater = baseContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(id, flBaseContent)
    }

}
