package io.husaynhakeem.mlkit_sample.ui.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

open class UserOption(
        @DrawableRes open val iconResId: Int,
        @StringRes open val title: Int,
        @StringRes open val body: Int)

data class MLKitApiOption(
        @DrawableRes override val iconResId: Int,
        @StringRes override val title: Int,
        @StringRes override val body: Int,
        val type: MLKitApiType,
        val isEnabled: Boolean) : UserOption(iconResId, title, body)

data class NewImageOption(
        @DrawableRes override val iconResId: Int,
        @StringRes override val title: Int,
        @StringRes override val body: Int) : UserOption(iconResId, title, body)
