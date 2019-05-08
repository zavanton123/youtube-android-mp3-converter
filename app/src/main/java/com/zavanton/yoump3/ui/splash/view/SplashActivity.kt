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
import com.zavanton.yoump3.utils.Logger

class SplashActivity : AppCompatActivity(), ISplashActivity {

    companion object {

        private const val TEXT_INTENT_TYPE = "text/plain"
    }

    lateinit var presenter: ISplashActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupPresenter()
        presenter.onViewCreated()

        checkPermissionsAndStartApp()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    override fun processIntentExtras() {
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                processActionSend(intent)
            }
            else -> {
                Logger.d("SplashActivity - no extras found")
            }
        }
    }

    override fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun repeatRequestPermissions() {
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
        goToMainActivity()
    }

    override fun onNegativeButtonClick() {
        this@SplashActivity.finish()
    }

    private fun setupPresenter() {
        presenter = ViewModelProviders.of(this)
            .get(SplashActivityViewModel::class.java)
            .presenter.apply {
            attach(this@SplashActivity)
        }
    }

    private fun processActionSend(intent: Intent) {
        Logger.d("SplashActivity - processActionSend")
        if (TEXT_INTENT_TYPE == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                Logger.d("SplashActivity - the extra contain this text: $it")
                presenter.processExtra(it)
            }
        }
    }

    private fun checkPermissionsAndStartApp() {
        presenter.checkPermissions(RxPermissions(this))
    }
}