package com.pr.paymentreminder.presentation.paymentreminder.prueba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.pr.paymentreminder.databinding.FragmentBaseComposeBinding

abstract class BaseComposeFragment : BaseBarOwnerFragment<FragmentBaseComposeBinding>() {

    override val getBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBaseComposeBinding =
        FragmentBaseComposeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            /* Dispose of the Composition when the view's lifecycleOwner is destroyed */
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { ScreenContent() }
        }
    }

    @Composable
    protected abstract fun ScreenContent()
}