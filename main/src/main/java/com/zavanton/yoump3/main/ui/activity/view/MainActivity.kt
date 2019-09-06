package com.zavanton.yoump3.main.ui.activity.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.main.ui.fragment.view.MainFragment
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d()

        setContentView(R.layout.ac_fragment_container)

        initViewModel()
        addFragment()
    }

    private fun initViewModel() {
        Log.d()
        viewModel = ViewModelProviders.of(this)
            .get(MainActivityViewModel::class.java)
    }

    private fun addFragment() {
        Log.d()
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}