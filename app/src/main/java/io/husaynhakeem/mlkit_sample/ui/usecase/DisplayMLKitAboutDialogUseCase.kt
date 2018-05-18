package io.husaynhakeem.mlkit_sample.ui.usecase

import io.husaynhakeem.mlkit_sample.core.model.MLKitApiOption
import io.husaynhakeem.mlkit_sample.core.wrapper.SharedPreferencesWrapper


object DisplayMLKitAboutDialogUseCase {

    fun shouldShowAboutDialogFor(option: MLKitApiOption): Boolean {
        val isOptionFirstCall = SharedPreferencesWrapper.getBoolean(option.type.name)
        if (isOptionFirstCall) {
            SharedPreferencesWrapper.put(option.type.name, false)
        }
        return isOptionFirstCall || !option.isEnabled
    }
}