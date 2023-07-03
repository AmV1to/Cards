package com.github.amvito.cards.cards.presentation

import com.github.amvito.cards.databinding.CardDetailsFragmentBinding
import com.google.android.material.textview.MaterialTextView

data class CardUi(
    private val expiration: String,
    private val number: String,
    private val owner: String,
    private val type: String
) {

    interface Mapper<T> {
        fun map(
            expiration: String,
            number: String,
            owner: String,
            type: String
        ): T

        class MapItem(
            private val materialTextView: MaterialTextView
        ) : Mapper<Unit> {
            override fun map(
                expiration: String,
                number: String,
                owner: String,
                type: String
            ) {
                materialTextView.text = owner
            }
        }

        // todo fix it
        class Compare(
            private val cardUi: CardUi
        ) : Mapper<Boolean> {
            override fun map(
                expiration: String,
                number: String,
                owner: String,
                type: String
            ): Boolean {
                return expiration == cardUi.expiration
                        && number == cardUi.number
                        && owner == cardUi.owner
                        && type == cardUi.type
            }
        }

        class Details(
            private val details: CardDetailsFragmentBinding,
        ) : Mapper<Unit> {
            override fun map(
                expiration: String,
                number: String,
                owner: String,
                type: String
            ) {
                with(details) {
                    numberTextView.text = number
                    expirationTextView.text = expiration
                    ownerTextView.text = owner
                    typeTextView.text = type
                }
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(expiration, number, owner, type)
    }
}