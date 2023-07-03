package com.github.amvito.cards.core

import android.content.Context

interface ManageResources {

    fun string(stringId: Int): String

    class Base(
        private val context: Context
    ): ManageResources {
        override fun string(stringId: Int): String {
            return context.getString(stringId)
        }
    }

}