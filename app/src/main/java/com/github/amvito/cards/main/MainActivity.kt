package com.github.amvito.cards.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.github.amvito.cards.R
import com.github.amvito.cards.core.ProvideViewModel

class MainActivity : AppCompatActivity(), ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = provideViewModel(MainViewModel::class.java, this)

        viewModel.observe(this) {
            it.navigate(supportFragmentManager, R.id.containerId)
        }

        viewModel.init(savedInstanceState == null)

    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as ProvideViewModel).provideViewModel(clazz, owner)
    }
}