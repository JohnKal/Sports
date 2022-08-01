package com.example.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sports.ui.main.sportViewer.SportViewerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SportViewerFragment.newInstance())
                .commitNow()
        }
    }
}