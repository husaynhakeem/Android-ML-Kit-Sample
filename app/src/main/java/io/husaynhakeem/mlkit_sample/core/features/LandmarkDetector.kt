package io.husaynhakeem.mlkit_sample.core.features

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions.LATEST_MODEL
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage


class LandmarkDetector : MLKitFeature<FirebaseVisionCloudLandmarkDetector, List<FirebaseVisionCloudLandmark>>() {

    override val processor: FirebaseVisionCloudLandmarkDetector
        get() = firebaseVisionInstance.getVisionCloudLandmarkDetector(
                FirebaseVisionCloudDetectorOptions.Builder()
                        .setModelType(LATEST_MODEL)
                        .setMaxResults(15)
                        .build())

    override fun process(image: FirebaseVisionImage): Task<List<FirebaseVisionCloudLandmark>> {
        return processor.detectInImage(image)
    }

    override fun onProcessSuccess(result: List<FirebaseVisionCloudLandmark>) {
        result.forEach {
            Log.d(TAG, "Landmark id: ${it.entityId}")

            val confidenceProbability = it.confidence * 100
            Log.d(TAG, "Probability of confidence: $confidenceProbability")

            val landmark = it.landmark
            Log.d(TAG, "The landmark is called $landmark")

            it.locations.forEach {
                Log.d(TAG, "Location(${it.latitude}, ${it.longitude})")
            }
        }
    }

    override fun onProcessFailure(exception: Exception) {
        Log.e(TAG, "Failed to detect landmarks in the image\nCause: ${exception.message}")
    }

    companion object {
        private val TAG = LandmarkDetector::class.java.simpleName
    }
}