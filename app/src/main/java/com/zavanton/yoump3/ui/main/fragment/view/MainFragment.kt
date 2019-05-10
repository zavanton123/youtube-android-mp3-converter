package com.zavanton.yoump3.ui.main.fragment.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.download.view.DownloadService
import com.zavanton.yoump3.ui.main.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.utils.Log
import kotlinx.android.synthetic.main.fmt_main.*

class MainFragment : Fragment(), IMainFragment {

    lateinit var presenter: IMainFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d()
        super.onCreate(savedInstanceState)

        setupPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d()
        return inflater.inflate(R.layout.fmt_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d()

        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d()

        presenter.detach()
    }

    private fun setupPresenter() {
        Log.d()
        presenter = ViewModelProviders.of(this)
            .get(MainFragmentViewModel::class.java)
            .presenter.apply {
            attach(this@MainFragment)
        }
    }

    private fun initUI() {
        Log.d()
        initToolbar()
        initFab()
    }

    private fun initToolbar() {
        Log.d()
        vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun initFab() {
        Log.d()
        vFab.setOnClickListener {
            presenter.startDownloadService()
        }
    }

    override fun startDownloadService() {
        Log.d()
        val intent = Intent(requireContext(), DownloadService::class.java)
        requireActivity().startService(intent)
    }

    override fun showClipboardEmpty() {
        Log.d()
        vBox.setImageResource(R.drawable.ic_warning)
        vStatus.text = getString(R.string.clipboard_empty)
    }

    override fun showClipboardNotEmpty() {
        Log.d()
        vBox.setImageResource(R.drawable.ic_ok)
        vStatus.text = getString(R.string.clipboard_full)
    }

    override fun showUrlValid() {
        Log.d()
        vStatus.text = getString(R.string.url_valid)
    }

    override fun showUrlInvalid() {
        Log.d()
        vStatus.text = getString(R.string.url_invalid)
    }

    override fun showDownloadStarted() {
        Log.d()
        vStatus.text = getString(R.string.download_started)
    }

    override fun showDownloadProgress(downloadProgress: String?) {
        Log.d()
        vStatus.text = getString(R.string.download_progress, downloadProgress)
    }

    override fun showDownloadSuccess() {
        Log.d()
        vStatus.text = getString(R.string.download_success)
    }

    override fun showDownloadError() {
        Log.d()
        vStatus.text = getString(R.string.download_error)
    }

    override fun showConversionStarted() {
        Log.d()
        vStatus.text = getString(R.string.conversion_started)
    }

    override fun showConversionProgress(conversionProgress: String?) {
        Log.d()
        vStatus.text = getString(R.string.conversion_progress, conversionProgress)
    }

    override fun showConversionSuccess() {
        Log.d()
        vStatus.text = getString(R.string.conversion_success)
    }

    override fun showConversionError() {
        Log.d()
        vStatus.text = getString(R.string.conversion_error)
    }
}