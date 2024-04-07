package com.pr.paymentreminder.providers

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

private const val PermissionRequestKey = "permissionRequest"

object Permissions {
    val Notification = listOf(
        Manifest.permission.POST_NOTIFICATIONS
    )
}

class PermissionsRequesterImpl @Inject constructor(
    private val currentActivityProvider: CurrentActivityProvider
) : PermissionsRequester, ActivityResultCallback<Map<String, Boolean>> {

    private lateinit var launcher: ActivityResultLauncher<Array<String>>
    private var continuation: CancellableContinuation<Boolean>? = null

    override fun onCreate(owner: LifecycleOwner) {
        val activity = currentActivityProvider.activity ?: return
        val contract = ActivityResultContracts.RequestMultiplePermissions()
        val registry = activity.activityResultRegistry
        launcher = registry.register(PermissionRequestKey, contract, this)
    }

    override suspend fun requestPermissions(permissions: List<String>): Boolean {
        if (checkPermissions(permissions)) return true

        return suspendCancellableCoroutine {
            continuation = it
            launcher.launch(permissions.toTypedArray())
        }
    }

    override fun checkPermissions(permissions: List<String>): Boolean {
        val activity = currentActivityProvider.activity ?: return false

        return permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onActivityResult(result: Map<String, Boolean>) {
        val continuation = continuation ?: return

        val isNotificationPermissionShown = result.filter { Permissions.Notification.contains(it.key) }.isNotEmpty()

        if (continuation.isActive) {
            continuation.resume(result.all { it.value })
        }

        this.continuation = null
    }
}