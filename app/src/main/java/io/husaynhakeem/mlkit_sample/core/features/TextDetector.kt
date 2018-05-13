package io.husaynhakeem.mlkit_sample.core.features

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector


class TextDetector : MLKitFeature<FirebaseVisionTextDetector, FirebaseVisionText>() {

    override val processor: FirebaseVisionTextDetector
        get() = firebaseVisionInstance.visionTextDetector

    override fun process(image: FirebaseVisionImage): Task<FirebaseVisionText> {
        return processor.detectInImage(image)
    }

    override fun onProcessSuccess(result: FirebaseVisionText) {
        recognizedTextAsSingleLine(result.blocks)
        recognizedTextAsBlocks(result.blocks)
    }

    private fun recognizedTextAsSingleLine(blocks: List<FirebaseVisionText.Block>): String = with(StringBuilder()) {
        blocks.forEach {
            it.lines.forEach {
                it.elements.forEach {
                    append(it.text).append(" ")
                }
            }
        }
        toString()
    }

    private fun recognizedTextAsBlocks(blocks: List<FirebaseVisionText.Block>): String = with(StringBuilder()) {
        blocks.forEach {
            append(it.text).append(" ")
        }
        toString()
    }

    override fun onProcessFailure(exception: Exception) {
        Log.e(TAG, "Failed to detect text in the image\nCause: ${exception.message}")
    }

    companion object {
        private val TAG = TextDetector::class.java.simpleName
    }
}