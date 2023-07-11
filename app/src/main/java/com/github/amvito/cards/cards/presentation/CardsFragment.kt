package com.github.amvito.cards.cards.presentation

import android.os.Bundle
import android.view.View
import com.github.amvito.cards.R
import com.github.amvito.cards.core.BaseFragment
import com.github.amvito.cards.databinding.CardFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardsFragment : BaseFragment() {

    private val viewModel: CardsViewModel by viewModel<CardsViewModel>()
    override val layoutId: Int = R.layout.card_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CardsAdapter(object : OnItemClickListener {
            override fun onClick(item: CardUi) {
                viewModel.showDetails(item)
            }
        })

        val binding = CardFragmentBinding.bind(view)

        binding.recyclerView.adapter = adapter

        binding.fetchButton.setOnClickListener {
            viewModel.fetchCards(
                binding.cardsCountEditText.text
                    .toString().toInt()
            )
        }

        viewModel.observeState(this) {
            it.map(CardUiState.Mapper.CardUiStateMapper(adapter, binding))
        }

        binding.tryAgainButton.setOnClickListener {
            viewModel.tryAgain(binding.cardsCountEditText.text
                .toString().toInt())
        }

        viewModel.observeProgress(this) {
            binding.progressBar.visibility = it
        }
    }
}