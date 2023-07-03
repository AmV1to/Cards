package com.github.amvito.cards.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return dependencyContainer.module(modelClass).viewModel() as T
    }
}