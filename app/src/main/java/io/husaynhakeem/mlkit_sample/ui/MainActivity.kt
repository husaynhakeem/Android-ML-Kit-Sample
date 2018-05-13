package io.husaynhakeem.mlkit_sample.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esafirm.imagepicker.features.ImagePicker
import io.husaynhakeem.mlkit_sample.R
import io.husaynhakeem.mlkit_sample.ui.data.UserOption
import io.husaynhakeem.mlkit_sample.ui.dialog.ImagePickerDialog
import io.husaynhakeem.mlkit_sample.ui.dialog.MLKitApiDefinitionDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, UserOptionViewHolder.Listener, ImagePickerDialog.Listener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.view = this
    }

    //======================================================
    //region MainView
    //======================================================
    override fun setUpUserOptionsList(options: Array<UserOption>) {
        with(userOptionsRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = UserOptionsAdapter(this@MainActivity, options)
        }
    }

    override fun showImagePicker() {
        ImagePickerDialog().show(supportFragmentManager, ImagePickerDialog::class.java.simpleName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            if (image != null) {
                selectedImageImageView.setImageBitmap(BitmapFactory.decodeFile(image.path))
                viewModel.imagePath = image.path
            }
        }
    }

    override fun printResult(result: String) {
        resultTextView.text = result
    }

    override fun printError(error: String) {
        resultTextView.text = error
    }

    override fun showMLKitApiDefinitionDialog(iconResId: Int, title: Int, body: Int) {
        with(MLKitApiDefinitionDialog()) {
            val bundle = Bundle()
            bundle.putInt(MLKitApiDefinitionDialog.KEY_TITLE, title)
            bundle.putInt(MLKitApiDefinitionDialog.KEY_BODY, body)
            arguments = bundle
            show(supportFragmentManager, MLKitApiDefinitionDialog::class.java.simpleName)
        }
    }
    //endregion

    //======================================================
    //region UserOptionViewHolder.Listener
    //======================================================
    override fun onClick(option: UserOption) {
        viewModel.onUserOptionSelected(option)
    }
    //endregion

    //======================================================
    //region ImagePickerDialog.Listener
    //======================================================
    override fun onCameraSelected() {
        ImagePicker.cameraOnly().start(this)
    }

    override fun onGallerySelected() {
        ImagePicker.create(this).showCamera(false).start()
    }
    //endregion
}
