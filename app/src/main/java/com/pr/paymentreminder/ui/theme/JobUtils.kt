package com.pr.paymentreminder.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun ViewModel.awaitAllAsyncJobs(vararg jobs: suspend () -> Unit) {
    jobs.map { job ->
        viewModelScope.async { job() }
    }.awaitAll()
}
