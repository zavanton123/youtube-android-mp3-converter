package com.zavanton.yoump3.ui.main.fragment

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
import com.zavanton.yoump3.ui.main.activity.MainActivity
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.viewModel.MainFragmentViewModel
import com.zavanton.yoump3.ui.main.fragment.viewModel.MainFragmentViewModelFactory

class MainFragment : Fragment() {

    companion object {

        private var mainFragmentComponent: MainFragmentComponent? = null

        fun getMainFragmentComponent() = mainFragmentComponent
    }

    private lateinit var model: MainFragmentViewModel

    private lateinit var bind: FmtMainBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initDependencies()

        bind = DataBindingUtil.inflate(inflater, R.layout.fmt_main, container, false)

        model = ViewModelProviders.of(this,
            MainFragmentViewModelFactory()
        )
            .get(MainFragmentViewModel::class.java)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeUI(model)

        model.init()
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
        bind.vToolbar.title = requireContext().getString(R.string.app_name)
    }

    private fun initFab() {
        bind.vFab.setOnClickListener {
            model.startDownloadService()
        }
    }

    private fun subscribeUI(model: MainFragmentViewModel) {
        model.isClipboardFull.observe(this, Observer { isClipboardFull ->
            if (isClipboardFull) {
                showFullClipboard()
            } else {
                showEmptyClipboard()
            }
        })
    }

    private fun showFullClipboard() {
        bind.vBox.setImageResource(R.drawable.ic_ok)
        bind.vClipboardState.text = getString(R.string.clipboard_full)
    }

    private fun showEmptyClipboard() {
        bind.vBox.setImageResource(R.drawable.ic_warning)
        bind.vClipboardState.text = getString(R.string.clipboard_empty)
    }
}