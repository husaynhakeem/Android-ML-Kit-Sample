package io.husaynhakeem.mlkit_sample.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import io.husaynhakeem.mlkit_sample.R


class MLKitApiDefinitionDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getInt(KEY_TITLE) ?: 0
        val body = arguments?.getInt(KEY_BODY) ?: 0
        return AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(body)
                .setPositiveButton(R.string.definition_dialog_button_label, { _, _ -> })
                .create()
    }

    companion object {
        const val KEY_TITLE = "key_title"
        const val KEY_BODY = "key_body"
    }
}