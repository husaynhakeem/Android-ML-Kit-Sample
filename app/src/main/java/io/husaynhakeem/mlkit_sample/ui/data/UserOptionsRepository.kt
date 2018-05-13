package io.husaynhakeem.mlkit_sample.ui.data

import io.husaynhakeem.mlkit_sample.R
import io.husaynhakeem.mlkit_sample.ui.data.MLKitApiType.*


object UserOptionsRepository {

    val options: Array<UserOption> by lazy {
        arrayOf(
                NewImageOption(
                        R.drawable.ic_launcher_background,
                        R.string.new_image_title,
                        R.string.new_image_body),
                MLKitApiOption(
                        R.drawable.ic_launcher_background,
                        R.string.barcode_detector_title,
                        R.string.barcode_detector_body,
                        BARCODE_DETECTOR,
                        true),
                MLKitApiOption(
                        R.drawable.ic_launcher_background,
                        R.string.face_detector_title,
                        R.string.face_detector_body,
                        FACE_DETECTOR,
                        true),
                MLKitApiOption(
                        R.drawable.ic_launcher_background,
                        R.string.image_labeler_title,
                        R.string.image_labeler_body,
                        IMAGE_LABELER,
                        true),
                MLKitApiOption(
                        R.drawable.ic_launcher_background,
                        R.string.landmark_detector_title,
                        R.string.landmark_detector_body,
                        LANDMARK_DETECTOR,
                        true),
                MLKitApiOption(
                        R.drawable.ic_launcher_background,
                        R.string.text_detector_title,
                        R.string.text_detector_body,
                        TEXT_DETECTOR,
                        true))
    }

    val firstMLKitApiOption: MLKitApiOption = options[1] as MLKitApiOption
}