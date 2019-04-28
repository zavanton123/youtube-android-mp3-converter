package com.zavanton.youtube_downloader.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zavanton.youtube_downloader.R
import com.zavanton.youtube_downloader.ui.main.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_fragment_container)

        addFragment()
    }

    private fun addFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}
