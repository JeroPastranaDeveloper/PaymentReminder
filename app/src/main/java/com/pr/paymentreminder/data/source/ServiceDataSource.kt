package com.pr.paymentreminder.data.source

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.data.model.Service
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ServiceDataSource @Inject constructor() {
    suspend fun getServices(): List<Service> {
        val database = Firebase.database
        val services = mutableListOf<Service>()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val servicesRef = database.getReference("$userId/servicios")

        suspendCoroutine { continuation ->
            servicesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = Service(
                            serviceSnapshot.child("categoria").value as String,
                            serviceSnapshot.child("color").value as String,
                            serviceSnapshot.child("fecha").value as String,
                            serviceSnapshot.child("nombre").value as String,
                            serviceSnapshot.child("precio").value as String,
                            serviceSnapshot.child("recordar").value as String,
                            serviceSnapshot.child("tipo").value as String
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