package io.husaynhakeem.mlkit_sample.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.husaynhakeem.mlkit_sample.ui.data.UserOption

interface MainView {

    fun setUpUserOptionsList(options: Array<UserOption>)

    fun showImagePicker()

    fun printResult(result: String)

    fun printError(error: String)

    fun showMLKitApiDefinitionDialog(@DrawableRes iconResId: Int, @StringRes title: Int, @StringRes body: Int)
}
