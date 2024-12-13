package com.mrtnmrls.devhub.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mrtnmrls.devhub.domain.model.Esp8266
import com.mrtnmrls.devhub.domain.UseCaseResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Esp8266RepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : Esp8266Repository {

    private val reference = firebaseDatabase.getReference(ESP8266_REFERENCE)

    override suspend fun fetchEsp8266Data(): Flow<UseCaseResult<Esp8266>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as? Map<String, Any>
                val esp8266 = Esp8266(
                    christmasLights = data?.get(CHRISTMAS_LIGHTS) as? Boolean ?: false,
                    currentDateTime = data?.get(CURRENT_DATE_TIME) as? String ?: "",
                    pinState = data?.get(PIN_STATE) as Boolean,
                    timeAlive = data?.get(TIME_ALIVE) as Double
                )
                trySend(UseCaseResult.Success(esp8266))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        reference.addValueEventListener(listener)
        awaitClose { reference.removeEventListener(listener) }
    }

    override suspend fun toggleChristmasLights(newValue: Boolean): UseCaseResult<Unit> =
        suspendCancellableCoroutine { continuation ->
            val updates = mapOf(CHRISTMAS_LIGHTS to newValue)

            reference.updateChildren(updates)
                .addOnSuccessListener {
                    continuation.resume(UseCaseResult.Success(Unit))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }


    companion object {
        private const val ESP8266_REFERENCE = "kasacho/esp8266"

        private const val CHRISTMAS_LIGHTS = "christmas-lights"
        private const val CURRENT_DATE_TIME = "current-date-time"
        private const val PIN_STATE = "pin-state"
        private const val TIME_ALIVE = "time-alive"
    }
}
