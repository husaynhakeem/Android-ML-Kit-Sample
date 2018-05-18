package io.husaynhakeem.mlkit_sample.ui

import io.husaynhakeem.mlkit_sample.core.model.MLKitApiOption
import io.husaynhakeem.mlkit_sample.core.model.UserOption
import io.husaynhakeem.mlkit_sample.ui.data.UserOptionsRepository


data class MainViewState(
        val userOptions: Array<UserOption> = UserOptionsRepository.options,
        var isLoading: Boolean = false,
        var imagePath: String = "",
        var result: String = "",
        var error: String = "",
        var mlKitApiOption: MLKitApiOption = UserOptionsRepository.firstMLKitApiOption,
        var displayAboutDialog: Boolean = false
)