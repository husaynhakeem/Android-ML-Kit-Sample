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
            val confidenceProbability = Math.round(it.confidence * 100)
            append("- A $label was detected in the image with a probability of $confidenceProbability%\n")
        }

        if (this.isBlank()) {
            return RESULT_TITLE + EMPTY_RESULT_MESSAGE
        }

        RESULT_TITLE + toString()
    }

    override fun onDetectionFailure(exception: Exception): String {
        return ERROR_MESSAGE + exception.message
    }

    companion object {
        private const val RESULT_TITLE = "Image labeling results\n\n"

        private const val EMPTY_RESULT_MESSAGE = "Failed to label objects in the provided image."

        private const val ERROR_MESSAGE = "An error occurred while trying to label objects in the provided image.\n\nCause: "
    }
}