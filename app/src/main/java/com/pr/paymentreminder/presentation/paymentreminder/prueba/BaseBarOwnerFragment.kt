package com.pr.paymentreminder.presentation.paymentreminder.prueba

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
abstract class BaseBarOwnerFragment<V : ViewBinding>: BaseBindingFragment<V>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}