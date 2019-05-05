package com.zavanton.yoump3.ui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.utils.Logger

class DemoActivity : AppCompatActivity(), Demo.MvpView {

    private var presenter: Demo.MvpPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("DemoActivity - onCreate")

        setContentView(R.layout.activity_demo)

        ViewModelProviders.of(this)

        setupPresenter()
    }

    private fun setupPresenter() {
        presenter = lastCustomNonConfigurationInstance as DemoPresenter?
        if (presenter == null) {
            presenter = DemoPresenter()
        }
        presenter?.attach(this)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        Logger.d("DemoActivity - onRetainCustomNonConfigurationInstance")

        return presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("DemoActivity - onDestroy")

        presenter?.detach(this)
    }
}
