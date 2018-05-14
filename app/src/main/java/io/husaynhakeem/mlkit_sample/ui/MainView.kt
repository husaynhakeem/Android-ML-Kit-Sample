package io.husaynhakeem.mlkit_sample.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.husaynhakeem.mlkit_sample.core.model.UserOption

interface MainView {

    fun setUpUserOptionsList(options: Array<UserOption>)

    fun showImagePicker(isCancelable: Boolean)

    fun printResult(result: String)

    fun printError(error: String)

    fun showMLKitApiAboutDialog(@DrawableRes iconResId: Int, @StringRes title: Int, @StringRes body: Int)

    fun showSelectedImage(imagePath: String)

    fun showLoader()

    fun dismissLoader()
}
