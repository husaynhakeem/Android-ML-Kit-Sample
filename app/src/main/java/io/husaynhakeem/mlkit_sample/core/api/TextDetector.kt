package io.husaynhakeem.mlkit_sample.core.api

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector
import io.husaynhakeem.mlkit_sample.core.visionimage.BitmapVisionImageGenerator


class TextDetector : MLKitApi<FirebaseVisionTextDetector, FirebaseVisionText>() {

    override val processor: FirebaseVisionTextDetector
        get() = firebaseVisionInstance.visionTextDetector

    override fun detectInImage(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit): Task<FirebaseVisionText> {
        return processor.detectInImage(BitmapVisionImageGenerator(image).get())
    }

    override fun onDetectionSuccess(result: FirebaseVisionText): String {
        val stringResult = recognizedTextAsBlocks(result)
        if (stringResult.isBlank()) {
            return RESULT_TITLE + EMPTY_RESULT_MESSAGE
        }
        return RESULT_TITLE + stringResult
    }

    private fun recognizedTextAsSingleLine(text: FirebaseVisionText): String = with(StringBuilder()) {
        text.blocks.forEach {
            it.lines.forEach {
                it.elements.forEach {
                    append(it.text).append(" ")
                }
            }
        }
        toString()
    }

    private fun recognizedTextAsBlocks(text: FirebaseVisionText): String = with(StringBuilder()) {

        text.blocks.forEach {
            append(it.text).append(" ")
        }

        toString()
    }

    override fun onDetectionFailure(exception: Exception): String {
        return ERROR_MESSAGE + exception.message
    }

    companion object {
        private const val RESULT_TITLE = "Text detection results\n\n"

        private const val EMPTY_RESULT_MESSAGE = "Failed to detect text in the provided image."

        private const val ERROR_MESSAGE = "An error occurred while trying to detect text in the provided image.\n\nCause: "
    }
}