package com.zavanton.yoump3.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.main.activity.view.MainActivity
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.Permissions.PERMISSIONS
import io.reactivex.disposables.CompositeDisposable

class SplashActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissionsAndStartApp()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun checkPermissionsAndStartApp() {
        compositeDisposable.add(
            RxPermissions(this).request(*PERMISSIONS)
                .subscribe(
                    { arePermissionsGranted ->
                        if (arePermissionsGranted) {
                            goToMainActivity()
                        } else {
                            repeatRequestPermissions()
                        }
                    },
                    { Logger.e("An error occurred while checking permissions", it) }
                )
        )
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun repeatRequestPermissions() {
        MaterialDialog.Builder(this)
            .iconRes(R.drawable.ic_action_warning)
            .limitIconToDefaultSize()
            .title(getString(R.string.need_permissions))
            .positiveText(getString(R.string.grant))
            .negativeText(getString(R.string.do_not_grant))
            .onPositive { _, _ -> onPositiveButtonClick() }
            .onNegative { _, _ -> onNegativeButtonClick() }
            .show()
    }

    private fun onPositiveButtonClick() {
        goToMainActivity()
    }

    private fun onNegativeButtonClick() {
        this@SplashActivity.finish()
    }
}