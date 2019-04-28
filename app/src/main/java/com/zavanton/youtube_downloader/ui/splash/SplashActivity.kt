package com.zavanton.youtube_downloader.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.youtube_downloader.ui.activity.MainActivity
import com.zavanton.youtube_downloader.utils.Logger
import io.reactivex.disposables.CompositeDisposable

class SplashActivity : AppCompatActivity() {

    companion object {

        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissionsAndStartApplication()
    }

    private fun checkPermissionsAndStartApplication() {
        compositeDisposable.add(
            RxPermissions(this).request(*PERMISSIONS).subscribe(
                { goToMainActivity() },
                { Logger.d("permissions are NOT granted") }
            )
        )
    }

    private fun goToMainActivity() {
        Logger.d("goToMainActivity")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("onDestroy")
        compositeDisposable.dispose()
    }
}