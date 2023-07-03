package com.github.amvito.cards.details.presentation

import android.os.Bundle
import android.view.View
import com.github.amvito.cards.R
import com.github.amvito.cards.cards.presentation.CardUi
import com.github.amvito.cards.core.BaseFragment
import com.github.amvito.cards.core.Communication
import com.github.amvito.cards.databinding.CardDetailsFragmentBinding

class CardDetailsFragment : BaseFragment<CardDetailsViewModel>() {
    override val viewModelClass: Class<CardDetailsViewModel> = CardDetailsViewModel::class.java
    override val layoutId: Int = R.layout.card_details_fragment

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