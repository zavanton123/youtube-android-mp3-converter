package com.zavanton.yoump3.ui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.utils.Logger

class DemoActivity : AppCompatActivity(), Demo.MvpView {

    private lateinit var presenter: Demo.MvpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("DemoActivity - onCreate")

        setContentView(R.layout.activity_demo)

        setupPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("DemoActivity - onDestroy")

        presenter.detach()
    }

    private fun setupPresenter() {
        val viewModel = ViewModelProviders.of(this).get(DemoViewModel::class.java)
        presenter = viewModel.presenter
        presenter.attach(this)
    }
}
