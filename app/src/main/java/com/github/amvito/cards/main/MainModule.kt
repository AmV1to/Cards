package com.github.amvito.cards.main

import com.github.amvito.cards.core.Module
import com.github.amvito.cards.core.ProvideNavigation

class MainModule(
    private val navigation: ProvideNavigation,
) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(navigation.navigation())
    }
}