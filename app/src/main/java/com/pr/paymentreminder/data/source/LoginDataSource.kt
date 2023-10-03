package com.pr.paymentreminder.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginDataSource @Inject constructor(){
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultLiveData.value = task.isSuccessful
            }

        return resultLiveData
    }
}