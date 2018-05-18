package io.husaynhakeem.mlkit_sample.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import io.husaynhakeem.mlkit_sample.ui.recycler.UserOptionsAdapter
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
        viewModel.viewState.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            renderUserOptions(it.userOptions)
            renderLoading(it.isLoading)
            renderSelectedImage(it.imagePath)
            renderResult(it.result)
            renderError(it.error)
            renderMLKitApiAboutDialog(it.displayAboutDialog, it.mlKitApiOption)
        })
    }

    //======================================================
    //region UI rendering
    //======================================================
    private fun renderUserOptions(options: Array<UserOption>) {
        if (userOptionsRecyclerView.adapter == null) {
            userOptionsRecyclerView.layoutManager = CenteredHorizontalLayoutManager(this@MainActivity)
            LinearSnapHelper().attachToRecyclerView(userOptionsRecyclerView)
            userOptionsRecyclerView.adapter = UserOptionsAdapter(options, { onUserOptionClicked(it) })
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        progressLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun renderSelectedImage(imagePath: String) {
        if (imagePath.isBlank()) {
            showImagePicker(false)
        } else {
            selectedImageImageView.setImageBitmap(BitmapFactory.decodeFile(imagePath))
        }
    }

    private fun showImagePicker(isCancelable: Boolean) {
        if (supportFragmentManager.findFragmentByTag(ImagePickerDialog.TAG) == null) {
            val imagePickerDialog = ImagePickerDialog()
            imagePickerDialog.isCancelable = isCancelable
            imagePickerDialog.show(supportFragmentManager, ImagePickerDialog.TAG)
        }
    }

    private fun onUserOptionClicked(userOption: UserOption) {
        when (userOption) {
            is NewImageOption -> showImagePicker(true)
            is MLKitApiOption -> viewModel.onMLKitApiOptionSelected(userOption)
        }
    }

    private fun renderResult(result: String) {
        if (result.isNotBlank())
            resultTextView.text = result
    }

    private fun renderError(error: String) {
        if (error.isNotBlank())
            resultTextView.text = error
    }

    private fun renderMLKitApiAboutDialog(displayAboutDialog: Boolean, option: MLKitApiOption) {
        if (displayAboutDialog)
            showMLKitAboutDialog(option)
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
    //endregion

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
                .theme(R.style.CameraPickerTheme)
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

    //======================================================
    //region Menu
    //======================================================
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemGithub -> {
                openGithubProfile()
                true
            }
            else -> false
        }
    }

    private fun openGithubProfile() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_profile_url)))
        startActivity(intent)
    }
    //endregion
}
