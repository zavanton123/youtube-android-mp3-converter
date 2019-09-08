package com.zavanton.yoump3.main.fragment.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.ui.view.DownloadService
import com.zavanton.yoump3.main.R
import com.zavanton.yoump3.main.fragment.ui.viewModel.IMainFragmentViewModel
import com.zavanton.yoump3.main.fragment.ui.viewModel.MainAction
import com.zavanton.yoump3.main.fragment.ui.viewModel.MainFragmentViewModel
import com.zavanton.yoump3.main.fragment.ui.viewModel.MainFragmentViewModelFactory
import kotlinx.android.synthetic.main.fmt_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: IMainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, MainFragmentViewModelFactory())
            .get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fmt_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()
        initUI()
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d()

        viewModel.onDestroyView()
    }

    private fun initUI() {
        Log.d()
        initToolbar()
        initFab()

        listenForActions()
    }

    private fun listenForActions() {
        viewModel.mainActionLiveData.observe(this,
            Observer<MainAction> { mainAction ->
                when (mainAction) {
                    MainAction.ShowClipboardNotEmpty -> showClipboardNotEmpty()
                    MainAction.ShowClipboardEmpty -> showClipboardEmpty()
                    MainAction.ShowUrlValid -> showUrlValid()
                    MainAction.ShowUrlInvalid -> showUrlInvalid()
                    MainAction.StartDownloadService -> startDownloadService()
                    MainAction.ShowDownloadStarted -> showDownloadStarted()
                    is MainAction.ShowDownloadProgress -> showDownloadProgress(mainAction.downloadProgress)
                    MainAction.ShowDownloadSuccess -> showDownloadSuccess()
                    MainAction.ShowDownloadError -> showDownloadError()
                    MainAction.ShowConversionStarted -> showConversionStarted()
                    is MainAction.ShowConversionProgress -> showConversionProgress(mainAction.conversionProgress)
                    MainAction.ShowConversionSuccess -> showConversionSuccess()
                    MainAction.ShowConversionError -> showConversionError()
                    MainAction.OtherAction -> Log.d("some other action")
                }
            })
    }

    private fun initToolbar() {
        Log.d()
        vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun initFab() {
        Log.d()
        vFab.setOnClickListener {
            viewModel.startDownloadService()
        }
    }

    private fun startDownloadService() {
        Log.d()
        val intent = Intent(requireContext(), DownloadService::class.java)
        requireActivity().startService(intent)
    }

    private fun showClipboardEmpty() {
        Log.d()
        vBox.setImageResource(R.drawable.ic_warning)
        vStatus.text = getString(R.string.clipboard_empty)
    }

    private fun showClipboardNotEmpty() {
        Log.d()
        vBox.setImageResource(R.drawable.ic_ok)
        vStatus.text = getString(R.string.clipboard_full)
    }

    private fun showUrlValid() {
        Log.d()
        vStatus.text = getString(R.string.url_valid)
    }

    private fun showUrlInvalid() {
        Log.d()
        vStatus.text = getString(R.string.url_invalid)
    }

    private fun showDownloadStarted() {
        Log.d()
        vStatus.text = getString(R.string.download_started)
    }

    private fun showDownloadProgress(downloadProgress: String?) {
        Log.d("$downloadProgress")
        vStatus.text = getString(R.string.download_progress, downloadProgress)
    }

    private fun showDownloadSuccess() {
        Log.d()
        vStatus.text = getString(R.string.download_success)
    }

    private fun showDownloadError() {
        Log.d()
        vStatus.text = getString(R.string.download_error)
    }

    private fun showConversionStarted() {
        Log.d()
        vStatus.text = getString(R.string.conversion_started)
    }

    private fun showConversionProgress(conversionProgress: String?) {
        Log.d()
        vStatus.text = getString(R.string.conversion_progress, conversionProgress)
    }

    private fun showConversionSuccess() {
        Log.d()
        vStatus.text = getString(R.string.conversion_success)
    }

    private fun showConversionError() {
        Log.d()
        vStatus.text = getString(R.string.conversion_error)
    }
}