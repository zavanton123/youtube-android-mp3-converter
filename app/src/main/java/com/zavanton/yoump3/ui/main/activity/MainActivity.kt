package com.zavanton.yoump3.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zavanton.yoump3.R
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.main.activity.di.MainActivityModule
import com.zavanton.yoump3.ui.main.activity.presenter.IMainActivityPresenter
import com.zavanton.yoump3.ui.main.fragment.MainFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @ActivityScope
    @Inject
    lateinit var presenter: IMainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_fragment_container)

        addFragment()

        presenter.onCreated()
    }

    private fun initDependencies() {
        TheApp.getAppComponent()
            .plusMainActivityComponent(MainActivityModule(this))
            .inject(this)
    }

    private fun addFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}
