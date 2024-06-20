package br.com.joaovq.uppersports.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class FirebaseAuthService(
    private val auth: FirebaseAuth
) {
    private val log = Timber.tag(this::class.java.simpleName)

    init {
        auth.useAppLanguage()
        log.d("Auth language code: ${auth.languageCode}")
    }

    suspend fun createAccount(name: String, email: String, password: String) =
        callbackFlow<AuthResponse<AuthResult>> {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                    )
                    launch {
                        send(AuthResponse.Success(authResult))
                    }
                }.addOnFailureListener { ex ->
                    ex.printStackTrace()
                    launch {
                        send(AuthResponse.Error(ex))
                        close(ex)
                    }
                }
            awaitClose()
        }

    suspend fun signIn(email: String, password: String) =
        callbackFlow<AuthResponse<AuthResult>> {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    launch {
                        send(AuthResponse.Success(authResult))
                    }
                }.addOnFailureListener { ex ->
                    ex.printStackTrace()
                    launch {
                        send(AuthResponse.Error(ex))
                        close(ex)
                    }
                }
            awaitClose()
        }
}

sealed interface AuthResponse<out T> {
    data class Success<T>(val data: T) : AuthResponse<T>
    data class Error(val ex: Exception) : AuthResponse<Nothing>
}