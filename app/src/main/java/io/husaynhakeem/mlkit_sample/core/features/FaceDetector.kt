package io.husaynhakeem.mlkit_sample.core.features

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions.*
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark


class FaceDetector : MLKitFeature<FirebaseVisionFaceDetector, List<FirebaseVisionFace>>() {

    override val processor: FirebaseVisionFaceDetector
        get() = firebaseVisionInstance.getVisionFaceDetector(
                FirebaseVisionFaceDetectorOptions.Builder()
                        .setModeType(ACCURATE_MODE)
                        .setLandmarkType(ALL_LANDMARKS)
                        .setClassificationType(ALL_CLASSIFICATIONS)
                        .setTrackingEnabled(true)
                        .build())

    override fun process(image: FirebaseVisionImage): Task<List<FirebaseVisionFace>> {
        return processor.detectInImage(image)
    }

    override fun onProcessSuccess(result: List<FirebaseVisionFace>) {
        result.forEach {
            Log.d(TAG, "Head id: ${it.trackingId}")

            val bounds = it.boundingBox
            val rotY = it.headEulerAngleY
            val rotZ = it.headEulerAngleZ

            Log.d(TAG, "Head is rotated to the right $rotY degrees")
            Log.d(TAG, "Head is tilted sideawys $rotZ degrees")

            Log.d(TAG, "Smiling probability ${it.smilingProbability}")
            Log.d(TAG, "Right eye open probablity ${it.rightEyeOpenProbability}")
            Log.d(TAG, "Left eye open probablity ${it.leftEyeOpenProbability}")

            val rightEar = it.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)
            val leftEar = it.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)

            rightEar?.position?.let {
                Log.d(TAG, "Right ear (${it.x}, ${it.y}, ${it.z})")
            }

            leftEar?.position?.let {
                Log.d(TAG, "Right ear (${it.x}, ${it.y}, ${it.z})")
            }
        }
    }

    override fun onProcessFailure(exception: Exception) {
        Log.e(TAG, "Failed to detect faces in the image\nCause: ${exception.message}")
    }

    companion object {
        private val TAG = FaceDetector::class.java.simpleName
    }
}