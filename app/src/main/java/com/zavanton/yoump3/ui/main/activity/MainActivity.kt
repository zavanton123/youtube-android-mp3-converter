package com.zavanton.yoump3.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zavanton.yoump3.R
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.ui.main.fragment.MainFragment
import com.zavanton.yoump3.utils.Logger

class MainActivity : AppCompatActivity() {

    private var mainActivityComponent: MainActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_fragment_container)

        addFragment()
    }

    override fun onDestroy() {
        super.onDestroy()

        mainActivityComponent = null
        Logger.d("mainActivityComponent: $mainActivityComponent")
    }

    fun getMainActivityComponent(): MainActivityComponent? = mainActivityComponent

    private fun initDependencies() {
        mainActivityComponent = TheApp.getAppComponent()
            .plusMainActivityComponent(MainActivityProvideModule(this))
            .apply {
                inject(this@MainActivity)
            }
    }

    private fun addFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}