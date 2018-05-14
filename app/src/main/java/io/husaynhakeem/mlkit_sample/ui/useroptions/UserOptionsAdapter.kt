package io.husaynhakeem.mlkit_sample.ui.useroptions

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.husaynhakeem.mlkit_sample.core.model.UserOption

class UserOptionsAdapter(
        private val options: Array<UserOption>,
        private val onItemClickListener: (UserOption) -> Unit) : RecyclerView.Adapter<UserOptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserOptionViewHolder(parent, onItemClickListener)

    override fun onBindViewHolder(holder: UserOptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount() = options.size
}
