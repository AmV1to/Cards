package com.github.amvito.cards.core

interface NavigationCommunication {

    interface Observe : Communication.Observe<Navigation>

    interface Put : Communication.Put<Navigation>

    interface Mutable : Observe, Put

    class Base : Communication.SingleUi<Navigation>(), Mutable

}