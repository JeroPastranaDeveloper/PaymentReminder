package com.pr.paymentreminder.presentation.paymentreminder.prueba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding

abstract class BaseBindingFragment<V : ViewBinding> : BaseFragment() {

    private var _binding: V? = null
    protected val binding: V get() = _binding ?: throw IllegalStateException()

    protected abstract val getBinding: (LayoutInflater, ViewGroup?, Boolean) -> V

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getBinding(inflater, container, false).also { _binding = it }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBindViews(root: View, savedInstanceState: Bundle?, isViewRestored: Boolean) {}
}