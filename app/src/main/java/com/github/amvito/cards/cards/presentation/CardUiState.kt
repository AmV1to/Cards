package com.github.amvito.cards.cards.presentation

import android.view.View
import com.github.amvito.cards.core.Update
import com.github.amvito.cards.databinding.CardFragmentBinding

interface CardUiState {

    interface Mapper<T> {

        fun map(list: List<CardUi>): T
        fun map(message: String): T

        class CardUiStateMapper(
            private val update: Update<List<CardUi>>,
            private val binding: CardFragmentBinding,
        ) : Mapper<Unit> {
            override fun map(list: List<CardUi>) {
                with(binding) {
                    exceptionLayout.visibility = View.GONE
                    editTextLayout.visibility = View.VISIBLE
                    fetchButton.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
                update.update(list)
            }

            override fun map(message: String) {
                with(binding) {
                    exceptionLayout.visibility = View.VISIBLE
                    editTextLayout.visibility = View.GONE
                    fetchButton.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    exceptionMessage.text = message
                }
            }
        }

    }

    fun <T : Any> map(mapper: Mapper<T>): T

    data class Success(
        private val list: List<CardUi>
    ) : CardUiState {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.map(list)
        }
    }

    data class Fail(
        private val errorMessage: String
    ) : CardUiState {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.map(errorMessage)
        }
    }

}