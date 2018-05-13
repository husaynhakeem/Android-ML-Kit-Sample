package io.husaynhakeem.mlkit_sample.core.api

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision


abstract class MLKitApi<P, T> {

    protected val firebaseVisionInstance = FirebaseVision.getInstance()

    protected abstract val processor: P

    protected abstract fun detectInImage(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit): Task<T>

    protected abstract fun onDetectionSuccess(result: T): String

    protected abstract fun onDetectionFailure(exception: Exception): String

    fun process(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        detectInImage(image, onSuccess, onFailure)
                .addOnSuccessListener { onSuccess(onDetectionSuccess(it)) }
                .addOnFailureListener { onFailure(onDetectionFailure(it)) }
    }
}