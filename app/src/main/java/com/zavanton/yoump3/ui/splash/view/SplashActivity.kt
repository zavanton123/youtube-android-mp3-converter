package com.zavanton.yoump3.ui.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.main.activity.view.MainActivity
import com.zavanton.yoump3.ui.splash.presenter.ISplashActivityPresenter
import com.zavanton.yoump3.utils.Log

class SplashActivity : AppCompatActivity(), ISplashActivity {

    companion object {

        private const val TEXT_INTENT_TYPE = "text/plain"
    }

    lateinit var presenter: ISplashActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d()

        setupPresenter()
        presenter.onViewCreated()

        checkPermissionsAndStartApp()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d()

        presenter.detach()
    }

    override fun processIntentExtras() {
        Log.d()
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                processActionSend(intent)
            }
            else -> {
                Log.d("no extras found")
            }
        }
    }

    override fun goToMainActivity() {
        Log.d()
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun repeatRequestPermissions() {
        Log.d()
        MaterialDialog.Builder(this)
            .iconRes(R.drawable.ic_action_warning)
            .limitIconToDefaultSize()
            .title(getString(R.string.need_permissions))
            .positiveText(getString(R.string.grant))
            .negativeText(getString(R.string.do_not_grant))
            .onPositive { _, _ -> presenter.onPositiveButtonClick() }
            .onNegative { _, _ -> presenter.onNegativeButtonClick() }
            .show()
    }

    override fun onPositiveButtonClick() {
        Log.d()
        goToMainActivity()
    }

    override fun onNegativeButtonClick() {
        Log.d()
        this@SplashActivity.finish()
    }

    private fun setupPresenter() {
        Log.d()
        presenter = ViewModelProviders.of(this)
            .get(SplashActivityViewModel::class.java)
            .presenter.apply {
            attach(this@SplashActivity)
        }
    }

    private fun processActionSend(intent: Intent) {
        Log.d("intent: $intent")
        if (TEXT_INTENT_TYPE == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                presenter.processExtra(it)
            }
        }
    }

    private fun checkPermissionsAndStartApp() {
        Log.d()
        presenter.checkPermissions(RxPermissions(this))
    }
}