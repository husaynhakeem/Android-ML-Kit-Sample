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

    override fun onDetectionSuccess(result: List<FirebaseVisionFace>) = with(StringBuilder()) {
        result.forEach {
            append("Head id: ${it.trackingId}\n")

            val bounds = it.boundingBox
            val rotY = Math.round(it.headEulerAngleY)
            val rotZ = Math.round(it.headEulerAngleZ)

            append("Head is rotated to the right $rotY degrees\n")
            append("Head is tilted sideways $rotZ degrees\n")

            val smilingProbability = Math.round(it.smilingProbability * 100)
            append("Smiling probability $smilingProbability%\n")

            val rightEyeOpenProbability = Math.round(it.rightEyeOpenProbability * 100)
            append("Right eye open probablity $rightEyeOpenProbability%\n")

            val leftEyeOpenProbability = Math.round(it.leftEyeOpenProbability * 100)
            append("Left eye open probablity $leftEyeOpenProbability%\n")

            val rightEar = it.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)
            val leftEar = it.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)

            rightEar?.position?.let {
                append("Right ear (${it.x}, ${it.y}, ${it.z})\n")
            }

            leftEar?.position?.let {
                append("Right ear (${it.x}, ${it.y}, ${it.z})\n")
            }

            append("\n")
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
        private const val RESULT_TITLE = "Face detection results\n\n"

        private const val EMPTY_RESULT_MESSAGE = "Failed to detect faces in the provided image."

        private const val ERROR_MESSAGE = "An error occurred while trying to detect faces in the provided image.\n\nCause: "
    }
}