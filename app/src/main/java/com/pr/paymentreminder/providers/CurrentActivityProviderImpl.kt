package com.pr.paymentreminder.providers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

class CurrentActivityProviderImpl @Inject constructor() : CurrentActivityProvider,
    ActivityLifecycleCallbacksAdapter() {

    private var currentActivity: WeakReference<Activity> = WeakReference(null)

    override val activity: FragmentActivity? get() = currentActivity.get() as? FragmentActivity

    override fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = WeakReference(activity)
    }
}