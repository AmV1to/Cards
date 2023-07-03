package com.github.amvito.cards.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.github.amvito.cards.core.Navigation
import com.github.amvito.cards.core.NavigationCommunication
import com.github.amvito.cards.core.Screen

class MainViewModel(
    private val navigationCommunication: NavigationCommunication.Mutable
) : ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            navigationCommunication.put(Navigation.Add(Screen.CardFragment))
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<Navigation>) {
        navigationCommunication.observe(owner, observer)
    }
}