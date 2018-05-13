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

    fun onUserOptionSelected(option: UserOption) {
        when (option) {
            is NewImageOption -> view?.showImagePicker()
            is MLKitApiOption -> MLkitApiFactory.get(option.type).process(
                    imagePath,
                    { view?.printResult(it) },
                    { view?.printError(it) })
        }
    }

    fun onImageSelected(imagePath: String) {
        this.imagePath = imagePath
        onUserOptionSelected(UserOptionsRepository.firstMLKitApiOption)
    }
}
