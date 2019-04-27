package com.zavanton.youtube_downloader

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {

        val LINK = "https://www.youtube.com/watch?v=IGQBtbKSVhY"
        val FORMAT_TAG = 22
    }

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->


            object : YouTubeExtractor(this) {
                public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                    if (ytFiles != null) {

                        val url = ytFiles[FORMAT_TAG].url
                    }
                }
            }.extract(LINK, true, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
