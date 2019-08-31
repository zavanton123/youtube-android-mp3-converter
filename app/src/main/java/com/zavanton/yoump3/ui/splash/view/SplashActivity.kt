package com.zavanton.yoump3.ui.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.main.activity.view.MainActivity
import com.zavanton.yoump3.utils.Log

class SplashActivity : AppCompatActivity() {

    companion object {

        private const val TEXT_INTENT_TYPE = "text/plain"
    }

    lateinit var viewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d()

        viewModel = ViewModelProviders.of(this, SplashActivityViewModelFactory())
            .get(SplashActivityViewModel::class.java)

        setupUi()

        viewModel.checkPermissions(RxPermissions(this))
    }

    private fun setupUi() {
        viewModel.splashEvent.observe(this,
            Observer<SplashEvent> { t ->
                when (t) {
                    SplashEvent.ProceedWithApp -> proceedWithApp()
                    SplashEvent.RepeatRequestPermissions -> repeatRequestPermissions()
                    SplashEvent.PositiveButtonClick -> onPositiveButtonClick()
                    SplashEvent.NegativeButtonClick -> onNegativeButtonClick()
                }
            })
    }

    private fun proceedWithApp() {
        Log.d()

        processIntentExtras()
        goToMainActivity()
    }

    private fun processIntentExtras() {
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

    private fun processActionSend(intent: Intent) {
        Log.d("intent: $intent")
        if (TEXT_INTENT_TYPE == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                viewModel.processExtra(it)
            }
        }
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun repeatRequestPermissions() {
        Log.d()
        MaterialDialog.Builder(this)
            .iconRes(R.drawable.ic_action_warning)
            .limitIconToDefaultSize()
            .title(getString(R.string.need_permissions))
            .positiveText(getString(R.string.grant))
            .negativeText(getString(R.string.do_not_grant))
            .onPositive { _, _ -> viewModel.onPositiveButtonClick() }
            .onNegative { _, _ -> viewModel.onNegativeButtonClick() }
            .show()
    }

    private fun onPositiveButtonClick() {
        Log.d()
        proceedWithApp()
    }

    private fun onNegativeButtonClick() {
        Log.d()
        this@SplashActivity.finish()
    }
}