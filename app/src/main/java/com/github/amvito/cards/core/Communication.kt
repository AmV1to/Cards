package com.github.amvito.cards.core

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

interface Communication {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Put<T> {
        fun put(source: T)
    }

    interface Mutable<T> : Observe<T>, Put<T>

    abstract class Abstract<T>(
        protected val liveData: MutableLiveData<T> = MutableLiveData()
    ) : Mutable<T> {
        override fun put(source: T) {
            liveData.value = source
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }


    abstract class Ui<T> : Abstract<T>()

    abstract class SingleUi<T> : Abstract<T>(SingleLiveEvent())
}

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(value: T) {
        mPending.set(true)
        super.setValue(value)
    }
}