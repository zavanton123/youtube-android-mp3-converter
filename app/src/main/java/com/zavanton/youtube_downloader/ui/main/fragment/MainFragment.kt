package com.zavanton.youtube_downloader.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zavanton.youtube_downloader.R
import com.zavanton.youtube_downloader.ui.service.DownloadService
import kotlinx.android.synthetic.main.fmt_main.view.*


class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fmt_main, container, false)

        initUI(view)

        return view
    }

    private fun initUI(view: View) {
        initToolbar(view)
        initImageView(view)
        initFab(view)
    }

    private fun initToolbar(view: View) {
        view.vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun initImageView(view: View) {
        // TODO
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
