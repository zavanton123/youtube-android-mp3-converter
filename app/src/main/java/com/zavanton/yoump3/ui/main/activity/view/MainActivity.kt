package com.zavanton.yoump3.ui.main.activity.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.main.fragment.view.MainFragment
import com.zavanton.yoump3.utils.Log

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