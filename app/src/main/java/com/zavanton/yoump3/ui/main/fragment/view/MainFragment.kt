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
import com.zavanton.yoump3.utils.Logger
import kotlinx.android.synthetic.main.fmt_main.*

class MainFragment : Fragment(), IMainFragment {

    lateinit var presenter: IMainFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.d("MainFragment - onCreate")
        super.onCreate(savedInstanceState)

        setupPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fmt_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    override fun onDestroy() {
        Logger.d("MainFragment - onDestroy")
        super.onDestroy()

        presenter.detach()
    }

    private fun setupPresenter() {
        presenter = ViewModelProviders.of(this)
            .get(MainFragmentViewModel::class.java)
            .presenter.apply {
            attach(this@MainFragment)
        }
    }

    private fun initUI() {
        initToolbar()
        initFab()
    }

    private fun initToolbar() {
        vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun initFab() {
        vFab.setOnClickListener {
            presenter.startDownloadService()
        }
    }

    override fun startDownloadService() {
        Logger.d("MainFragment - startDownloadService")
        val intent = Intent(requireContext(), DownloadService::class.java)
        requireActivity().startService(intent)
    }

    override fun showClipboardEmpty() {
        Logger.d("showClipboardEmpty")
        vBox.setImageResource(R.drawable.ic_warning)
        vClipboardState.text = getString(R.string.clipboard_empty)
    }

    override fun showClipboardNotEmpty() {
        Logger.d("showClipboardNotEmpty")
        vBox.setImageResource(R.drawable.ic_ok)
        vClipboardState.text = getString(R.string.clipboard_full)
    }

    override fun showUrlValid() {
        TODO("not implemented")
    }

    override fun showUrlInvalid() {
        TODO("not implemented")
    }

    override fun showDownloadStarted() {
        TODO("not implemented")
    }

    override fun showDownloadProgress(downloadProgress: String?) {
        TODO("not implemented")
    }

    override fun showDownloadSuccess() {
        TODO("not implemented")
    }

    override fun showDownloadError() {
        TODO("not implemented")
    }

    override fun showConversionStarted() {
        TODO("not implemented")
    }

    override fun showConversionProgress(conversionProgress: String?) {
        TODO("not implemented")
    }

    override fun showConversionSuccess() {
        TODO("not implemented")
    }

    override fun showConversionError() {
        TODO("not implemented")
    }
}