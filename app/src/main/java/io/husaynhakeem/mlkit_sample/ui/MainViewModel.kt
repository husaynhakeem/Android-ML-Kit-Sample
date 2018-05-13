package io.husaynhakeem.mlkit_sample.ui

import androidx.lifecycle.ViewModel
import io.husaynhakeem.mlkit_sample.core.api.MLkitApiFactory
import io.husaynhakeem.mlkit_sample.core.model.MLKitApiOption
import io.husaynhakeem.mlkit_sample.core.model.NewImageOption
import io.husaynhakeem.mlkit_sample.core.model.UserOption

class MainViewModel : ViewModel() {

    var view: MainView? = null
        set(value) {
            field = value
            field?.setUpUserOptionsList(UserOptionsRepository.options)
            if (imagePath.isBlank()) {
                field?.showImagePicker()
            } else {
                field?.showSelectedImage(imagePath)
            }
        }

    var imagePath: String = ""
        set(value) {
            field = value
            view?.showSelectedImage(imagePath)
            onUserOptionSelected(mostRecentlyUsedMLKitApiOption)
        }

    var mostRecentlyUsedMLKitApiOption = UserOptionsRepository.firstMLKitApiOption

    fun onUserOptionSelected(option: UserOption) {
        when (option) {
            is NewImageOption -> view?.showImagePicker()
            is MLKitApiOption -> {
                mostRecentlyUsedMLKitApiOption = option
                showMLKitApiDefinition(option)
                MLkitApiFactory.get(option.type).process(
                        imagePath,
                        { view?.printResult(it) },
                        { view?.printError(it) })
            }
        }
    }

    private fun showMLKitApiDefinition(option: MLKitApiOption) {
        if (AboutMLKitApisHandler.shouldShowAboutDialogFor(option)) {
            view?.showMLKitApiAboutDialog(option.iconResId, option.title, option.body)
        }
    }
}
