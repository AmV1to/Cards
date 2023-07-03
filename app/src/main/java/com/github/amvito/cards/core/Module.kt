package com.github.amvito.cards.core

import androidx.lifecycle.ViewModel

interface Module <T : ViewModel> {

    fun viewModel(): T

}