package com.zavanton.yoump3.ui.main.fragment.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavanton.yoump3.R
import com.zavanton.yoump3.ui.download.service.DownloadService
import com.zavanton.yoump3.ui.main.activity.MainActivity
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentPresenter
import com.zavanton.yoump3.utils.Logger
import kotlinx.android.synthetic.main.fmt_main.*
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainFragmentMvpView {

    private var mainFragmentComponent: MainFragmentComponent? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fmt_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()

        mainFragmentComponent = null
    }

    private fun initDependencies() {
        mainFragmentComponent = (requireActivity() as MainActivity).getMainActivityComponent()
            ?.plusMainFragmentComponent(MainFragmentProvideModule())
            ?.apply {
                inject(this@MainFragment)
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

    override fun showFullClipboard() {
        Logger.d("showFullClipboard")
        vBox.setImageResource(R.drawable.ic_ok)
        vClipboardState.text = getString(R.string.clipboard_full)
    }

    override fun showEmptyClipboard() {
        Logger.d("showEmptyClipboard")
        vBox.setImageResource(R.drawable.ic_warning)
        vClipboardState.text = getString(R.string.clipboard_empty)
    }
}