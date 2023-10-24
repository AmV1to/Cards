package com.github.amvito.cards.cards.presentation

import android.view.View
import com.github.amvito.cards.core.Update
import com.github.amvito.cards.databinding.CardFragmentBinding

interface CardUiState {

    fun apply(
        binding: CardFragmentBinding,
        update: Update<List<CardUi>>
    )

    abstract class Abstract(
        private val visibilityException: Int,
        private val visibilitySuccess: Int,
    ) : CardUiState {
        override fun apply(binding: CardFragmentBinding, update: Update<List<CardUi>>) {
            with(binding) {
                exceptionLayout.visibility = visibilityException
                editTextLayout.visibility = visibilitySuccess
                fetchButton.visibility = visibilitySuccess
                recyclerView.visibility = visibilitySuccess
            }
        }
    }

    data class Success(
        private val list: List<CardUi>
    ) : Abstract(View.GONE, View.VISIBLE) {
        override fun apply(binding: CardFragmentBinding, update: Update<List<CardUi>>) {
            super.apply(binding, update)
            update.update(list)
        }
    }

    data class Fail(
        private val errorMessage: String
    ) : Abstract(View.VISIBLE, View.GONE) {
        override fun apply(binding: CardFragmentBinding, update: Update<List<CardUi>>) {
            super.apply(binding, update)
            binding.exceptionMessage.text = errorMessage
        }
    }
}