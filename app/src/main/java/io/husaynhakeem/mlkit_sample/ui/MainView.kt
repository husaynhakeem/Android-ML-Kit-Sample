package io.husaynhakeem.mlkit_sample.ui

import io.husaynhakeem.mlkit_sample.ui.data.UserOption

interface MainView {

    fun setUpUserOptionsList(options: Array<UserOption>)

    fun showImagePicker()

    fun printResult(result: String)

    fun printError(error: String)
}
