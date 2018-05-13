package io.husaynhakeem.mlkit_sample.core.visionimage

import com.google.firebase.ml.vision.common.FirebaseVisionImage


interface FirebaseVisionImageGenerator {

    fun get() : FirebaseVisionImage
}