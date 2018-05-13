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

    override fun onProcessSuccess(result: FirebaseVisionText): String {
        return recognizedTextAsBlocks(result)
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

    override fun onProcessFailure(exception: Exception): String {
        return "Failed to detect text in the image\nCause: ${exception.message}"
    }
}