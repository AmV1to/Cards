package com.github.amvito.cards.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class Cards : Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelFactory
    private lateinit var dependencyContainer: DependencyContainer

    override fun onCreate() {
        super.onCreate()

        dependencyContainer = DependencyContainer.Base(Core.Base(this))
        viewModelsFactory = ViewModelFactory(dependencyContainer)
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, viewModelsFactory)[clazz]
    }
}