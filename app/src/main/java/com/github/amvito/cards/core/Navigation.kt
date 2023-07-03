package com.github.amvito.cards.core

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface Navigation {

    fun navigate(supportFragmentManager: FragmentManager, containerId: Int)

    abstract class Abstract(
        protected open val screen: Screen,
    ) : Navigation {

        override fun navigate(supportFragmentManager: FragmentManager, containerId: Int) {
            supportFragmentManager.beginTransaction()
                .executeTransaction(containerId)
                .commit()
        }

        abstract fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction

    }

    class Replace(override val screen: Screen) : Abstract(screen) {
        override fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction {
            return screen.fragment().let {
                replace(containerId, it.newInstance())
                    .addToBackStack(it.simpleName)
            }
        }
    }

    class Add(override val screen: Screen) : Abstract(screen) {
        override fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction {
            return add(containerId, screen.fragment().newInstance())
        }
    }
}