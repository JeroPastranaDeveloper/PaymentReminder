package com.pr.paymentreminder.presentation.paymentreminder.prueba

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    private val viewRestored = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindViews(view, savedInstanceState, viewRestored.getAndSet(true))
    }
    fun isViewRestored(): Boolean = viewRestored.get()

    abstract fun onBindViews(root: View, savedInstanceState: Bundle?, isViewRestored: Boolean)

    fun launchDelayed(timeInMillis: Long = 100, run: () -> Unit) = runBlocking {
        lifecycleScope.launch {
            withResumed {
                launchLooperDelayed(timeInMillis, run)
            }
        }
    }

    private fun launchLooperDelayed(timeInMillis: Long = 100, run: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            run()
        }, timeInMillis)
    }
}