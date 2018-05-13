package io.husaynhakeem.mlkit_sample.ui

import io.husaynhakeem.mlkit_sample.core.agent.SharedPreferencesAgent
import io.husaynhakeem.mlkit_sample.ui.data.MLKitApiOption


object MLKitApiDefinitionHandler {

    fun shouldShowMLKitApiOptionDefinition(option: MLKitApiOption): Boolean {
        val isMLKitApiOptionFirstCall = SharedPreferencesAgent.getBoolean(option.type.name)
        if (isMLKitApiOptionFirstCall) {
            SharedPreferencesAgent.put(option.type.name, false)
        }
        return isMLKitApiOptionFirstCall || !option.isEnabled
    }
}