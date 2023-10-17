package com.github.amvito.cards.cards.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface ObserveCards {
    fun observeState(owner: LifecycleOwner, observe: Observer<CardUiState>)
    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)
}