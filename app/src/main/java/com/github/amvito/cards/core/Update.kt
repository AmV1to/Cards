package com.github.amvito.cards.core

interface Update<T> {

    fun update(data: T)
}