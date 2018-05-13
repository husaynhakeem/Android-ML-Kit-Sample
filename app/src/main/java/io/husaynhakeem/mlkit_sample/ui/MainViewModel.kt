package io.husaynhakeem.mlkit_sample.ui

import androidx.lifecycle.ViewModel
import io.husaynhakeem.mlkit_sample.core.api.MLkitApiFactory
import io.husaynhakeem.mlkit_sample.ui.data.MLKitApiOption
import io.husaynhakeem.mlkit_sample.ui.data.NewImageOption
import io.husaynhakeem.mlkit_sample.ui.data.UserOption
import io.husaynhakeem.mlkit_sample.ui.data.UserOptionsRepository

class MainViewModel : ViewModel() {

    var view: MainView? = null
        set(value) {
            field = value
            field?.setUpUserOptionsList(UserOptionsRepository.options)
            if (imagePath.isBlank()) {
                field?.showImagePicker()
            }
        }

    var imagePath: String = ""
        set(value) {
            field = value
            onUserOptionSelected(UserOptionsRepository.firstMLKitApiOption)
        }

    fun onUserOptionSelected(option: UserOption) {
        when (option) {
            is NewImageOption -> view?.showImagePicker()
            is MLKitApiOption -> {
                showMLKitApiDefinition(option)
                MLkitApiFactory.get(option.type).process(
                        imagePath,
                        { view?.printResult(it) },
                        { view?.printError(it) })
            }
        }
    }

    private fun showMLKitApiDefinition(option: MLKitApiOption) {
        if (MLKitApiDefinitionHandler.shouldShowMLKitApiOptionDefinition(option)) {
            view?.showMLKitApiDefinitionDialog(option.iconResId, option.title, option.body)
        }
    }
}
