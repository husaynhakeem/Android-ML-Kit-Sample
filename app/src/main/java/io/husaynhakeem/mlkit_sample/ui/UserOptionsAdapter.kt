package io.husaynhakeem.mlkit_sample.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.husaynhakeem.mlkit_sample.ui.data.UserOption

class UserOptionsAdapter(
        private val listener: UserOptionViewHolder.Listener,
        private val options: Array<UserOption>) : RecyclerView.Adapter<UserOptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserOptionViewHolder(listener, parent)

    override fun onBindViewHolder(holder: UserOptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount() = options.size
}
