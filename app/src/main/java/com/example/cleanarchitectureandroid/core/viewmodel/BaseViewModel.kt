package com.example.cleanarchitectureandroid.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureandroid.core.dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Base abstractions for all ViewModels.
 */
abstract class BaseViewModel(private val dispatcher: Dispatcher) : ViewModel() {

    fun launchOnMain(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.main, coroutine)

    fun launchOnBack(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.io, coroutine)

    fun launchOnComputation(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.computation, coroutine)

    fun launchOnDb(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.db, coroutine)

    private fun launchOnViewModelScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutine: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineDispatcher, block = coroutine)
}
