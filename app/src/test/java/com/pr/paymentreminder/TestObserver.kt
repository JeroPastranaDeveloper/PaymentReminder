package com.pr.paymentreminder

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TestObserver<T>(private val dispatcher: CoroutineDispatcher) {

    private var _values: List<T> = emptyList()
    val values: List<T> get() = _values

    val first: T get() = values.first()
    val last: T get() = values.last()

    fun observe(flow: Flow<T>) = CoroutineScope(dispatcher).launch {
        flow.collect { _values = _values + it }
    }
}
