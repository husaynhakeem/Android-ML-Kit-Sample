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
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), MainView, UserOptionViewHolder.Listener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
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
        ImagePicker.create(this).showCamera(false).start()
        //ImagePicker.cameraOnly().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            if (image != null) {
                selectedImageImageView.setImageBitmap(BitmapFactory.decodeFile(image.path))
                viewModel.onImageSelected(image.path)
            }
        }
    }

    override fun printResult(result: String) {
        resultTextView.text = result
    }

    override fun printError(error: String) {
        resultTextView.text = error
    }
    //endregion

    //======================================================
    //region UserOptionViewHolder.Listener
    //======================================================
    override fun onClick(option: UserOption) {
        viewModel.onUserOptionSelected(option)
    }
    //endregion
}
