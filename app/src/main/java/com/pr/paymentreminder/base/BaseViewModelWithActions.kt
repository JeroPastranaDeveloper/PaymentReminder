package com.pr.paymentreminder.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModelWithActions<S, I, A> : BaseViewModel<S, I>() {

    final override val _state: MutableStateFlow<S> by lazy { MutableStateFlow(initialViewState) }
    final override val state: StateFlow<S> get() = _state

    private val pendingActions: MutableList<A> = mutableListOf()
    private val _actions: MutableSharedFlow<A> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val actions: Flow<A> get() = _actions

    protected abstract val initialViewState: S

    init {
        viewModelScope.launch {
            _actions.subscriptionCount.firstOrNull { it > 0 }?.let {
                pendingActions.forEach(::dispatchAction)
                pendingActions.clear()
            }
        }
    }

    protected fun setState(newState: S.() -> S) {
        _state.update(newState)
    }

    protected fun dispatchAction(action: A) {
        if (_actions.hasSubscribers) _actions.tryEmit(action) else pendingActions.add(action)
    }

    protected inline fun <reified T> SavedStateHandle.getArgs(): T? = get("args")

    protected inline fun <reified T> SavedStateHandle.requireArgs(): T =
        getArgs() ?: throw IllegalArgumentException()

    private val <T> MutableSharedFlow<T>.hasSubscribers: Boolean
        get() = subscriptionCount.value > 0
}