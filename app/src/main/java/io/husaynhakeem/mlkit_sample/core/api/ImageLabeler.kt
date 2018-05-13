package io.husaynhakeem.mlkit_sample.core.api

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions
import io.husaynhakeem.mlkit_sample.core.visionimage.BitmapVisionImageGenerator


class ImageLabeler : MLKitApi<FirebaseVisionLabelDetector, List<FirebaseVisionLabel>>() {

    override val processor: FirebaseVisionLabelDetector
        get() = firebaseVisionInstance.getVisionLabelDetector(
                FirebaseVisionLabelDetectorOptions.Builder()
                        .setConfidenceThreshold(0.5f)
                        .build())

    override fun detectInImage(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit): Task<List<FirebaseVisionLabel>> {
        return processor.detectInImage(BitmapVisionImageGenerator(image).get())
    }

    override fun onDetectionSuccess(result: List<FirebaseVisionLabel>) = with(StringBuilder()) {
        result.forEach {
            val label = it.label
            val confidenceProbability = it.confidence * 100
            append("$label was detected in the image with a probability of $confidenceProbability")
        }
        toString()
    }

    override fun onDetectionFailure(exception: Exception): String {
        return "Failed to detect objects in the image\nCause: ${exception.message}"
    }
}