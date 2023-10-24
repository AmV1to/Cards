package com.github.amvito.cards.core

import android.content.Context
import androidx.annotation.StringRes

interface ManageResources {

    fun string(@StringRes stringId: Int): String

    class Base(
        private val context: Context
    ): ManageResources {
        override fun string(@StringRes stringId: Int): String {
            return context.getString(stringId)
        }
    }

}