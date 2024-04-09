package com.pr.paymentreminder.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

val LocalFragment = staticCompositionLocalOf<Fragment?> { null }

inline fun <reified T : Any> Fragment.findInHierarchy(): T? = findInHierarchy(T::class)

@Suppress("UNCHECKED_CAST")
fun <T : Any> Fragment.findInHierarchy(kClass: KClass<T>): T? {
    if (kClass.isInstance(this)) return this as T
    if (parentFragment != null) return parentFragment?.findInHierarchy(kClass)
    if (kClass.isInstance(activity)) return activity as T
    return null
}