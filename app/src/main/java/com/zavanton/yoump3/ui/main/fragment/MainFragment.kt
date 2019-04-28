package com.zavanton.yoump3.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.zavanton.yoump3.R
import com.zavanton.yoump3.databinding.FmtMainBinding
import com.zavanton.yoump3.ui.service.DownloadService

class MainFragment : Fragment() {

    private lateinit var bind: FmtMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bind = DataBindingUtil.inflate<FmtMainBinding>(inflater, R.layout.fmt_main, container, false)

        initUI()

        return bind.root
    }

    private fun initUI() {
        initToolbar()
        showClipboardStatus()
        initFab()
    }

    private fun initToolbar() {
        bind.vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun showClipboardStatus() {
        val isEmpty = false
        if (isEmpty) {
            showEmptyClipboard()
        } else {
            showFullClipboard()
        }
    }

    private fun showFullClipboard() {
        bind.vBox.setImageResource(R.drawable.ic_full_box)
        bind.vClipboardState.text = getString(R.string.clipboard_full)
    }

    private fun showEmptyClipboard() {
        bind.vBox.setImageResource(R.drawable.ic_empty_box)
        bind.vClipboardState.text = getString(R.string.clipboard_empty)
    }

    private fun initFab() {
        bind.vFab.setOnClickListener {
            startDownloadService()
        }
    }

    private fun startDownloadService() {
        Intent(requireContext(), DownloadService::class.java).apply {
            requireActivity().startService(this)
        }
    }
}
