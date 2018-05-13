package io.husaynhakeem.mlkit_sample.core.api

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions.LATEST_MODEL
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import io.husaynhakeem.mlkit_sample.core.visionimage.BitmapVisionImageGenerator


class LandmarkDetector : MLKitApi<FirebaseVisionCloudLandmarkDetector, List<FirebaseVisionCloudLandmark>>() {

    override val processor: FirebaseVisionCloudLandmarkDetector
        get() = firebaseVisionInstance.getVisionCloudLandmarkDetector(
                FirebaseVisionCloudDetectorOptions.Builder()
                        .setModelType(LATEST_MODEL)
                        .setMaxResults(15)
                        .build())

    override fun detectInImage(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit): Task<List<FirebaseVisionCloudLandmark>> {
        return processor.detectInImage(BitmapVisionImageGenerator(image).get())
    }

    override fun onDetectionSuccess(result: List<FirebaseVisionCloudLandmark>) = with(StringBuilder()) {
        result.forEach {
            append("Landmark id: ${it.entityId}")

            val confidenceProbability = it.confidence * 100
            append("Probability of confidence: $confidenceProbability")

            val landmark = it.landmark
            append("The landmark is called $landmark")

            it.locations.forEach {
                append("Location(${it.latitude}, ${it.longitude})")
            }
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
        private const val RESULT_TITLE = "Landmark detection results\n\n"

        private const val EMPTY_RESULT_MESSAGE = "Failed to detect landmarks in the provided image."

        private const val ERROR_MESSAGE = "An error occurred while trying to detect landmarks in the provided image.\n\nCause: "
    }
}