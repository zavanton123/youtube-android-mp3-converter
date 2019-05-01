package com.zavanton.yoump3.ui.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zavanton.yoump3.R
import com.zavanton.yoump3.databinding.FmtMainBinding
import com.zavanton.yoump3.di.qualifier.ActivityContext
import com.zavanton.yoump3.di.qualifier.ApplicationContext
import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.activity.MainActivity
import com.zavanton.yoump3.ui.main.fragment.di.MainFragmentModule
import com.zavanton.yoump3.ui.main.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.ui.service.DownloadService
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var bind: FmtMainBinding
    private lateinit var model: MainFragmentViewModel

    @FragmentScope
    @Inject
    lateinit var presenter: IMainFragmentPresenter

    @FragmentScope
    @Inject
    @field:ApplicationContext
    lateinit var applicationContext: Context

    @FragmentScope
    @Inject
    @field:ActivityContext
    lateinit var activityContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initDependencies()

        bind = DataBindingUtil.inflate(inflater, R.layout.fmt_main, container, false)

        model = ViewModelProviders.of(this)
            .get(MainFragmentViewModel::class.java)

        return bind.root
    }

    private fun initDependencies() {
        (requireActivity() as MainActivity).getMainActivityComponent()
            .plusMainFragmentComponent(MainFragmentModule())
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeUI(model)

        model.init()
        presenter.onViewCreated()

        Logger.d("applicationContext: $applicationContext")
        Logger.d("activityContext: $activityContext")
    }

    private fun initUI() {
        initToolbar()
        initFab()
    }

    private fun initToolbar() {
        bind.vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun initFab() {
        bind.vFab.setOnClickListener {
            startDownloadService()
        }
    }

    private fun subscribeUI(model: MainFragmentViewModel) {
        model.getClipboardStatus().observe(this, Observer { isClipboardFull ->
            if (isClipboardFull) {
                showFullClipboard()
            } else {
                showEmptyClipboard()
            }
        })
    }

    private fun showFullClipboard() {
        bind.vBox.setImageResource(R.drawable.ic_full_box)
        bind.vClipboardState.text = getString(R.string.clipboard_full)
    }

    private fun showEmptyClipboard() {
        bind.vBox.setImageResource(R.drawable.ic_empty_box)
        bind.vClipboardState.text = getString(R.string.clipboard_empty)
    }

    private fun startDownloadService() {
        Intent(requireContext(), DownloadService::class.java).apply {
            requireActivity().startService(this)
        }
    }
}