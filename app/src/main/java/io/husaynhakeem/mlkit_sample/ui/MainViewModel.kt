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
                field?.showImagePicker(false)
            } else {
                populateView(field)
            }
        }

    var imagePath: String = ""
        set(value) {
            field = value
            view?.showSelectedImage(imagePath)
            onUserOptionSelected(mostRecentlyUsedMLKitApiOption)
        }

    var mostRecentlyUsedMLKitApiOption = UserOptionsRepository.firstMLKitApiOption
        set(value) {
            field = value
            showMLKitApiAboutDialog(field)
            processImage(field)
        }

    var latestResult: String = ""
        set(value) {
            field = value
            if (field.isNotEmpty())
                latestErrorMessage = ""
        }

    var latestErrorMessage: String = ""
        set(value) {
            field = value
            if (field.isNotEmpty())
                latestResult = ""
        }

    private fun populateView(view: MainView?) {
        view?.showSelectedImage(imagePath)
        if (latestResult.isNotEmpty())
            view?.printResult(latestResult)
        if (latestErrorMessage.isNotEmpty())
            view?.printError(latestErrorMessage)
    }

    private fun showMLKitApiAboutDialog(option: MLKitApiOption) {
        if (AboutMLKitApisHandler.shouldShowAboutDialogFor(option)) {
            view?.showMLKitApiAboutDialog(option.iconResId, option.title, option.body)
        }
    }

    private fun processImage(option: MLKitApiOption) {
        if (option.isEnabled) {
            view?.showLoader()
            MLkitApiFactory.get(option.type).process(
                    imagePath,
                    {
                        latestResult = it
                        view?.dismissLoader()
                        view?.printResult(it)
                    },
                    {
                        latestErrorMessage = it
                        view?.dismissLoader()
                        view?.printError(it)
                    })
        }
    }

    fun onUserOptionSelected(option: UserOption) {
        when (option) {
            is NewImageOption -> view?.showImagePicker(true)
            is MLKitApiOption -> mostRecentlyUsedMLKitApiOption = option
        }
    }
}
