package com.pr.paymentreminder.providers

import androidx.lifecycle.DefaultLifecycleObserver

interface PermissionsRequester : DefaultLifecycleObserver {
    suspend fun requestPermissions(permissions: List<String>): Boolean
    fun checkPermissions(permissions: List<String>): Boolean
}