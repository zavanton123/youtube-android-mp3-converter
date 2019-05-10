package com.zavanton.yoump3.ui.main.activity.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.main.fragment.view.MainFragment
import com.zavanton.yoump3.utils.Logger

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("MainActivity - onCreate")
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