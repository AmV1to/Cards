package com.github.amvito.cards.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.amvito.cards.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.observe(this) {
            it.navigate(supportFragmentManager, R.id.containerId)
        }

        viewModel.init(savedInstanceState == null)

    }
}