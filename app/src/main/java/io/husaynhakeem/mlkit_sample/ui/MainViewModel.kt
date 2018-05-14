package io.husaynhakeem.mlkit_sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.husaynhakeem.mlkit_sample.core.api.MLkitApiFactory
import io.husaynhakeem.mlkit_sample.core.model.MLKitApiOption
import io.husaynhakeem.mlkit_sample.core.model.UserOption

class MainViewModel : ViewModel() {

    val userOptions: LiveData<Array<UserOption>> by lazy {
        MutableLiveData<Array<UserOption>>().apply {
            postValue(UserOptionsRepository.options)
        }
    }

    val latestImagePath: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = ""
        }
    }

    var latestResult: MutableLiveData<String> = MutableLiveData()

    val latestErrorMessage: MutableLiveData<String> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val displayableAboutDialog: MutableLiveData<MLKitApiOption?> = MutableLiveData()

    private var latestMLKitApiOption = UserOptionsRepository.firstMLKitApiOption

    fun onImageSelected(imagePath: String) {
        latestImagePath.value = imagePath
        processImage()
    }

    private fun processImage() {
        if (latestMLKitApiOption.isEnabled) {
            isLoading.value = true
            MLkitApiFactory.get(latestMLKitApiOption.type).process(
                    latestImagePath.value!!,
                    {
                        isLoading.value = false
                        latestResult.value = it
                    },
                    {
                        isLoading.value = false
                        latestErrorMessage.value = it
                    })
        }
    }

    fun onMLKitApiOptionSelected(option: MLKitApiOption) {
        latestMLKitApiOption = option
        showMLKitApiAboutDialog()
        processImage()
    }

    private fun showMLKitApiAboutDialog() {
        if (AboutMLKitApisHandler.shouldShowAboutDialogFor(latestMLKitApiOption)) {
            displayableAboutDialog.postValue(latestMLKitApiOption)
        }
    }

    fun onMLKitAboutDialogDismissed() {
        displayableAboutDialog.value = null
    }
}
