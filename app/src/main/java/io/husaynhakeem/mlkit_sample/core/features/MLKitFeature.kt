package io.husaynhakeem.mlkit_sample.core.features

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage


abstract class MLKitFeature<P, T> {

    protected val firebaseVisionInstance = FirebaseVision.getInstance()

    protected abstract val processor: P

    abstract fun process(image: FirebaseVisionImage): Task<T>

    abstract fun onProcessSuccess(result: T)

    abstract fun onProcessFailure(exception: Exception)
}