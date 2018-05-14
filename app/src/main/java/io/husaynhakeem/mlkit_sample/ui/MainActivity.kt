package io.husaynhakeem.mlkit_sample.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearSnapHelper
import com.esafirm.imagepicker.features.ImagePicker
import io.husaynhakeem.mlkit_sample.R
import io.husaynhakeem.mlkit_sample.core.model.MLKitApiOption
import io.husaynhakeem.mlkit_sample.core.model.NewImageOption
import io.husaynhakeem.mlkit_sample.core.model.UserOption
import io.husaynhakeem.mlkit_sample.core.ui.CenteredHorizontalLayoutManager
import io.husaynhakeem.mlkit_sample.ui.dialog.ImagePickerDialog
import io.husaynhakeem.mlkit_sample.ui.dialog.MLKitApiAboutDialog
import io.husaynhakeem.mlkit_sample.ui.useroptions.UserOptionsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImagePickerDialog.Listener, MLKitApiAboutDialog.Listener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setupBackgroundClickListener()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeSelectedImageChanges()
        observeUserOptionsChanges()
        observeResultChanges()
        observeErrorMessageChanges()
        observeLoadingStateChanges()
        observeDisplayableAboutDialogChanges()
    }

    private fun observeSelectedImageChanges() {
        viewModel.latestImagePath.observe(this, Observer {
            if (it.isNullOrBlank()) {
                showImagePicker(false)
            } else {
                selectedImageImageView.setImageBitmap(BitmapFactory.decodeFile(it))
            }
        })
    }

    private fun showImagePicker(isCancelable: Boolean) {
        if (supportFragmentManager.findFragmentByTag(ImagePickerDialog.TAG) == null) {
            val imagePickerDialog = ImagePickerDialog()
            imagePickerDialog.isCancelable = isCancelable
            imagePickerDialog.show(supportFragmentManager, ImagePickerDialog.TAG)
        }
    }

    private fun observeUserOptionsChanges() {
        viewModel.userOptions.observe(this, Observer {
            userOptionsRecyclerView.layoutManager = CenteredHorizontalLayoutManager(this@MainActivity)
            LinearSnapHelper().attachToRecyclerView(userOptionsRecyclerView)
            userOptionsRecyclerView.adapter = UserOptionsAdapter(it!!, { onUserOptionClicked(it) })
        })
    }

    private fun onUserOptionClicked(userOption: UserOption) {
        when (userOption) {
            is NewImageOption -> showImagePicker(true)
            is MLKitApiOption -> viewModel.onMLKitApiOptionSelected(userOption)
        }
    }

    private fun observeResultChanges() {
        viewModel.latestResult.observe(this, Observer {
            resultTextView.text = it
        })
    }

    private fun observeErrorMessageChanges() {
        viewModel.latestErrorMessage.observe(this, Observer {
            resultTextView.text = it
        })
    }

    private fun observeDisplayableAboutDialogChanges() {
        viewModel.displayableAboutDialog.observe(this, Observer {
            it?.let { showMLKitAboutDialog(it) }
        })
    }

    private fun showMLKitAboutDialog(option: MLKitApiOption) {
        if (supportFragmentManager.findFragmentByTag(MLKitApiAboutDialog.TAG) == null) {
            val dialog = MLKitApiAboutDialog()
            val bundle = Bundle()
            bundle.putInt(MLKitApiAboutDialog.KEY_TITLE, option.title)
            bundle.putInt(MLKitApiAboutDialog.KEY_BODY, option.body)
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, MLKitApiAboutDialog.TAG)
        }
    }

    private fun observeLoadingStateChanges() {
        viewModel.isLoading.observe(this, Observer {
            it?.let {
                progressLoader.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }


    private fun setupBackgroundClickListener() {
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
                viewModel.onImageSelected(image.path)
            }
        }
    }

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

    //======================================================
    //region MLKitApiAboutDialog.Listener
    //======================================================
    override fun onDismissed() {
        viewModel.onMLKitAboutDialogDismissed()
    }
    //endregion
}
