package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orElse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ServicesDataSource @Inject constructor() {
    @ExperimentalCoroutinesApi
    fun getServices(): kotlinx.coroutines.flow.Flow<List<Service>> {
        val database = Firebase.database
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val servicesRef = database.getReference("$userId/${Constants.SERVICES}")

        return callbackFlow {
            val listener = servicesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val services = mutableListOf<Service>()
                    for (serviceSnapshot in snapshot.children) {
                        val imageUriString = serviceSnapshot.child(Constants.IMAGE).value as? String? ?: emptyString()
                        val service = Service(
                            serviceSnapshot.key.orElse { emptyString() },
                            serviceSnapshot.child(Constants.CATEGORY).value as? String ?: emptyString(),
                            serviceSnapshot.child(Constants.COLOR).value as? String ?: emptyString(),
                            serviceSnapshot.child(Constants.DATE).value as? String ?: emptyString(),
                            serviceSnapshot.child(Constants.NAME).value as? String ?: emptyString(),
                            serviceSnapshot.child(Constants.PRICE).value as? String ?: emptyString(),
                            serviceSnapshot.child(Constants.REMEMBER).value as? String ?: emptyString(),
                            serviceSnapshot.child(Constants.TYPE).value as? String ?: emptyString(),
                            imageUriString,
                        )
                        services.add(service)
                    }
                    trySend(services).isSuccess
                }

                override fun onCancelled(databaseError: DatabaseError) { /* nothing */ }
            })

            awaitClose { servicesRef.removeEventListener(listener) }
        }
    }

    fun createService(id: String, service: Service) {
        val database = Firebase.database
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val servicesRef = database.getReference("$userId/${Constants.SERVICES}")
        servicesRef.child(id).setValue(service)
    }

    fun updateService(serviceId: String, newServiceData: Service) {
        val database = Firebase.database
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val servicesRef = database.getReference("$userId/${Constants.SERVICES}")
        servicesRef.child(serviceId).setValue(newServiceData)
    }

    fun deleteService(serviceId: String) {
        val database = Firebase.database
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val servicesRef = database.getReference("$userId/${Constants.SERVICES}")
        servicesRef.child(serviceId).removeValue()
    }
}