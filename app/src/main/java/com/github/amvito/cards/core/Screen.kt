package com.github.amvito.cards.core

import com.github.amvito.cards.cards.presentation.CardsFragment


interface Screen {

    fun fragment(): Class<out BaseFragment<*>>

    object CardFragment : Screen {
        override fun fragment(): Class<out BaseFragment<*>> {
            return CardsFragment::class.java
        }
    }

    object CardDetailsFragment : Screen {
        override fun fragment(): Class<out BaseFragment<*>> {
            return com.github.amvito.cards.details.presentation.CardDetailsFragment::class.java
        }
    }
}