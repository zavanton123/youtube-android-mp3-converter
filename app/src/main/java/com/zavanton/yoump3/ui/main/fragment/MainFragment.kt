package com.zavanton.yoump3.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.service.DownloadService
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.fmt_main.view.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fmt_main, container, false)

        initUI(view)

        return view
    }

    private fun initUI(view: View) {
        initToolbar(view)
        showClipboardStatus(view)
        initFab(view)
    }

    private fun initToolbar(view: View) {
        view.vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun showClipboardStatus(view: View) {
        val isEmpty = false
        if (isEmpty) {
            showEmptyClipboard(view)
        } else {
            showFullClipboard(view)
        }
    }

    private fun showFullClipboard(view: View) {
        view.vBox.setImageResource(R.drawable.ic_full_box)
        view.vClipboardState.text = getString(R.string.clipboard_full)
    }

    private fun showEmptyClipboard(view: View) {
        view.vBox.setImageResource(R.drawable.ic_empty_box)
        view.vClipboardState.text = getString(R.string.clipboard_empty)
    }

    private fun initFab(view: View) {
        view.vFab.setOnClickListener {
            startDownloadService()
        }
    }

    private fun startDownloadService() {
        Intent(requireContext(), DownloadService::class.java).apply {
            requireActivity().startService(this)
        }
    }
}
