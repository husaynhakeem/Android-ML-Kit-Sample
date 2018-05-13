package io.husaynhakeem.mlkit_sample.core.api

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions.*
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import io.husaynhakeem.mlkit_sample.core.visionimage.BitmapVisionImageGenerator


class FaceDetector : MLKitApi<FirebaseVisionFaceDetector, List<FirebaseVisionFace>>() {

    override val processor: FirebaseVisionFaceDetector
        get() = firebaseVisionInstance.getVisionFaceDetector(
                FirebaseVisionFaceDetectorOptions.Builder()
                        .setModeType(ACCURATE_MODE)
                        .setLandmarkType(ALL_LANDMARKS)
                        .setClassificationType(ALL_CLASSIFICATIONS)
                        .setTrackingEnabled(true)
                        .build())

    override fun detectInImage(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit): Task<List<FirebaseVisionFace>> {
        return processor.detectInImage(BitmapVisionImageGenerator(image).get())
    }

    override fun onProcessSuccess(result: List<FirebaseVisionFace>) = with(StringBuilder()) {
        result.forEach {
            append("Head id: ${it.trackingId}")

            val bounds = it.boundingBox
            val rotY = it.headEulerAngleY
            val rotZ = it.headEulerAngleZ

            append("Head is rotated to the right $rotY degrees")
            append("Head is tilted sideawys $rotZ degrees")

            append("Smiling probability ${it.smilingProbability}")
            append("Right eye open probablity ${it.rightEyeOpenProbability}")
            append("Left eye open probablity ${it.leftEyeOpenProbability}")

            val rightEar = it.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)
            val leftEar = it.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)

            rightEar?.position?.let {
                append("Right ear (${it.x}, ${it.y}, ${it.z})")
            }

            leftEar?.position?.let {
                append("Right ear (${it.x}, ${it.y}, ${it.z})")
            }
        }
        toString()
    }

    override fun onProcessFailure(exception: Exception): String {
        return "Failed to detect faces in the image\nCause: ${exception.message}"
    }
}