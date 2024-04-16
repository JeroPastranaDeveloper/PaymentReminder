package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orElse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ServicesDataSource @Inject constructor() {
    private val services = mutableListOf<Service>()

    private fun getServicesRef(): DatabaseReference {
        val database = Firebase.database
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        return database.getReference("$userId/${Constants.SERVICES}")
    }

    @ExperimentalCoroutinesApi
    fun getServices(): Flow<List<Service>> {
        val servicesRef = getServicesRef()

        return callbackFlow {
            val listener = servicesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    services.clear()
                    for (serviceSnapshot in snapshot.children) {
                        val service = snapshotToService(serviceSnapshot)
                        services.add(service)
                    }
                    trySend(services).isSuccess
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error taking services")
                }
            })

            awaitClose { servicesRef.removeEventListener(listener) }
        }
    }

    fun getService(id: String): Flow<Service> {
        val serviceRef = getServicesRef().child(id)

        return callbackFlow {
            val listener = serviceRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val service = snapshotToService(snapshot)
                    trySend(service).isSuccess
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error obteniendo el servicio")
                }
            })

            awaitClose { serviceRef.removeEventListener(listener) }
        }
    }

    fun createService(id: String, service: Service) {
        getServicesRef().child(id).setValue(service)
    }

    fun updateService(serviceId: String, newServiceData: Service) {
        getServicesRef().child(serviceId).setValue(newServiceData)
    }

    fun removeService(serviceId: String) {
        getServicesRef().child(serviceId).removeValue()
    }

    private fun snapshotToService(snapshot: DataSnapshot): Service {
        val imageUriString = snapshot.child(Constants.IMAGE).value as? String? ?: emptyString()
        val url = snapshot.child(Constants.URL).value as? String? ?: emptyString()
        return Service(
            snapshot.key.orElse { emptyString() },
            snapshot.child(Constants.CATEGORY).value as? String ?: emptyString(),
            snapshot.child(Constants.COLOR).value as? String ?: emptyString(),
            snapshot.child(Constants.DATE).value as? String ?: emptyString(),
            snapshot.child(Constants.NAME).value as? String ?: emptyString(),
            snapshot.child(Constants.PRICE).value as? String ?: emptyString(),
            snapshot.child(Constants.REMEMBER).value as? String ?: emptyString(),
            snapshot.child(Constants.TYPE).value as? String ?: emptyString(),
            imageUriString,
            snapshot.child(Constants.COMMENTS).value as? String ?: emptyString(),
            url
        )
    }
}