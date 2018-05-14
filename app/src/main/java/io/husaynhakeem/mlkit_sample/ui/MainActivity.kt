package io.husaynhakeem.mlkit_sample.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearSnapHelper
import com.esafirm.imagepicker.features.ImagePicker
import io.husaynhakeem.mlkit_sample.R
import io.husaynhakeem.mlkit_sample.core.model.UserOption
import io.husaynhakeem.mlkit_sample.core.ui.CenteredHorizontalLayoutManager
import io.husaynhakeem.mlkit_sample.ui.dialog.ImagePickerDialog
import io.husaynhakeem.mlkit_sample.ui.dialog.MLKitApiAboutDialog
import io.husaynhakeem.mlkit_sample.ui.useroptions.UserOptionViewHolder
import io.husaynhakeem.mlkit_sample.ui.useroptions.UserOptionsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, UserOptionViewHolder.Listener, ImagePickerDialog.Listener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setUpUI()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.view = this
    }

    private fun setUpUI() {
        resultTextView.setOnClickListener {
            userOptionsRecyclerView.visibility =
                    if (userOptionsRecyclerView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            if (image != null) {
                viewModel.imagePath = image.path
            }
        }
    }

    //======================================================
    //region MainView
    //======================================================
    override fun setUpUserOptionsList(options: Array<UserOption>) {
        with(userOptionsRecyclerView) {
            layoutManager = CenteredHorizontalLayoutManager(this@MainActivity)
            LinearSnapHelper().attachToRecyclerView(this)
            setHasFixedSize(true)
            adapter = UserOptionsAdapter(this@MainActivity, options)
        }
    }

    override fun showImagePicker(isCancelable: Boolean) {
        val imagePickerDialog = ImagePickerDialog()
        imagePickerDialog.isCancelable = isCancelable
        imagePickerDialog.show(supportFragmentManager, ImagePickerDialog::class.java.simpleName)
    }

    override fun showSelectedImage(imagePath: String) {
        selectedImageImageView.setImageBitmap(BitmapFactory.decodeFile(imagePath))
    }

    override fun printResult(result: String) {
        resultTextView.text = result
    }

    override fun printError(error: String) {
        resultTextView.text = error
    }

    override fun showMLKitApiAboutDialog(iconResId: Int, title: Int, body: Int) {
        with(MLKitApiAboutDialog()) {
            val bundle = Bundle()
            bundle.putInt(MLKitApiAboutDialog.KEY_TITLE, title)
            bundle.putInt(MLKitApiAboutDialog.KEY_BODY, body)
            arguments = bundle
            show(supportFragmentManager, MLKitApiAboutDialog::class.java.simpleName)
        }
    }

    override fun showLoader() {
        progressLoader.visibility = View.VISIBLE
    }

    override fun dismissLoader() {
        progressLoader.visibility = View.GONE
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
        ImagePicker.create(this)
                .showCamera(false)
                .start()
    }
    //endregion
}
