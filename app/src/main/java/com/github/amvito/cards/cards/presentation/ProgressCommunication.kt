package com.github.amvito.cards.cards.presentation

import com.github.amvito.cards.core.Communication

interface ProgressCommunication : Communication.Mutable<Int> {
    class Base : Communication.Ui<Int>(), ProgressCommunication
}