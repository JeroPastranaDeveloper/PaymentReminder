package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ServicesDataSource @Inject constructor() {
    suspend fun getServices(): List<Service> {
        val database = Firebase.database
        val services = mutableListOf<Service>()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val servicesRef = database.getReference("$userId/${Constants.SERVICES}")

        suspendCoroutine { continuation ->
            servicesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = Service(
                            serviceSnapshot.child(Constants.CATEGORY).value as String,
                            serviceSnapshot.child(Constants.COLOR).value as String,
                            serviceSnapshot.child(Constants.DATE).value as String,
                            serviceSnapshot.child(Constants.NAME).value as String,
                            serviceSnapshot.child(Constants.PRICE).value as String,
                            serviceSnapshot.child(Constants.REMEMBER).value as String,
                            serviceSnapshot.child(Constants.TYPE).value as String
                        )
                        services.add(service)
                    }
                    continuation.resume(Unit)
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
        return services
    }
}