package com.zavanton.youtube_downloader.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.youtube_downloader.R
import com.zavanton.youtube_downloader.ui.service.DownloadService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)


        fab.setOnClickListener {
            checkPermissionsAndStartDownload()
        }
    }

    private fun checkPermissionsAndStartDownload() {
        val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        RxPermissions(this).request(*permissions).subscribe(
            {
                Log.d("zavanton", "permissions are granted")
                val intent = Intent(this, DownloadService::class.java)
                startService(intent)
            },
            {
                Log.d("zavanton", "permissions are NOT granted")
            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
