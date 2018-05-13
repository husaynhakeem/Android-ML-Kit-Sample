package io.husaynhakeem.mlkit_sample.core.features

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions


class ImageLabeler : MLKitFeature<FirebaseVisionLabelDetector, List<FirebaseVisionLabel>>() {
    override val processor: FirebaseVisionLabelDetector
        get() = firebaseVisionInstance.getVisionLabelDetector(
                FirebaseVisionLabelDetectorOptions.Builder()
                        .setConfidenceThreshold(0.5f)
                        .build())

    override fun process(image: FirebaseVisionImage): Task<List<FirebaseVisionLabel>> {
        return processor.detectInImage(image)
    }

    override fun onProcessSuccess(result: List<FirebaseVisionLabel>) {
        result.forEach {
            val label = it.label
            val confidenceProbability = it.confidence * 100
            Log.d(TAG, "$label was detected in the image with a probability of $confidenceProbability")
        }
    }

    override fun onProcessFailure(exception: Exception) {
        Log.e(TAG, "Failed to detect objects in the image\nCause: ${exception.message}")
    }

    companion object {
        private val TAG = ImageLabeler::class.java.simpleName
    }
}