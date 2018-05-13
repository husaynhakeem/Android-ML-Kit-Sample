package io.husaynhakeem.mlkit_sample.ui

import io.husaynhakeem.mlkit_sample.core.wrapper.SharedPreferencesWrapper
import io.husaynhakeem.mlkit_sample.core.model.MLKitApiOption


object AboutMLKitApisHandler {

    fun shouldShowAboutDialogFor(option: MLKitApiOption): Boolean {
        val isOptionFirstCall = SharedPreferencesWrapper.getBoolean(option.type.name)
        if (isOptionFirstCall) {
            SharedPreferencesWrapper.put(option.type.name, false)
        }
        return isOptionFirstCall || !option.isEnabled
    }
}