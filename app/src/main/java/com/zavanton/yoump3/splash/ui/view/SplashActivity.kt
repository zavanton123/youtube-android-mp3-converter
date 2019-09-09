package com.zavanton.yoump3.splash.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.zavanton.yoump3.R
import com.zavanton.yoump3.business.model.PermissionEvent
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.activity.ui.MainActivity
import com.zavanton.yoump3.splash.ui.viewModel.SplashActivityViewModel
import com.zavanton.yoump3.splash.ui.viewModel.SplashActivityViewModelFactory

class SplashActivity : AppCompatActivity() {

    companion object {

        private const val TEXT_INTENT_TYPE = "text/plain"
    }

    lateinit var viewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, SplashActivityViewModelFactory(this))
            .get(SplashActivityViewModel::class.java)

        listenForSplashEvents()

        viewModel.checkPermissions()
    }

    private fun listenForSplashEvents() {
        viewModel.permissionEvent.observe(this,
            Observer<PermissionEvent> {
                when (it) {
                    PermissionEvent.PermissionGranted -> proceedWithApp()
                    PermissionEvent.RepeatPermissionRequest -> repeatPermissionRequest()
                }
            }
        )
    }

    private fun processActionSend(intent: Intent) {
        Log.d("intent: $intent")
        if (TEXT_INTENT_TYPE == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                viewModel.processIntentExtra(it)
            }
        }
    }

    private fun repeatPermissionRequest() {
        MaterialDialog.Builder(this)
            .iconRes(R.drawable.ic_action_warning)
            .limitIconToDefaultSize()
            .title(getString(R.string.need_permissions))
            .positiveText(getString(R.string.grant))
            .negativeText(getString(R.string.do_not_grant))
            .onPositive { _, _ -> proceedWithApp() }
            .onNegative { _, _ -> finish() }
            .show()
    }

    private fun proceedWithApp() {
        processIntentExtras()
        goToMainActivity()
    }

    private fun processIntentExtras() {
        if (intent?.action == Intent.ACTION_SEND) {
            processActionSend(intent)
        } else {
            Log.d("no extras found")
        }
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}