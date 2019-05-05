package com.zavanton.yoump3.ui.main.activity.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.main.fragment.view.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_fragment_container)

        initViewModel()
        addFragment()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(MainActivityViewModel::class.java)
    }

    private fun addFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}