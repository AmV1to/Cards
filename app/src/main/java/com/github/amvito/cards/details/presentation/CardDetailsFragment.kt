package com.github.amvito.cards.details.presentation

import android.os.Bundle
import android.view.View
import com.github.amvito.cards.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.amvito.cards.cards.presentation.CardUi
import com.github.amvito.cards.core.BaseFragment
import com.github.amvito.cards.core.Communication
import com.github.amvito.cards.databinding.CardDetailsFragmentBinding

class CardDetailsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.card_details_fragment
    private val viewModel: CardDetailsViewModel by viewModel<CardDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = CardDetailsFragmentBinding.bind(view)

        viewModel.observeCard(this) {
            it.map(CardUi.Mapper.Details(binding))
        }
    }
}

interface CardUiCommunication : Communication.Mutable<CardUi> {
    class Base : Communication.Ui<CardUi>(), CardUiCommunication
}