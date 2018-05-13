package io.husaynhakeem.mlkit_sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.husaynhakeem.mlkit_sample.R
import io.husaynhakeem.mlkit_sample.ui.data.UserOption
import kotlinx.android.synthetic.main.item_user_option.view.*

class UserOptionViewHolder(private val listener: Listener, parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_option, parent, false)) {

    fun bind(option: UserOption) {
        itemView.userOptionImageView.setImageResource(option.iconResId)
        itemView.setOnClickListener { listener.onClick(option) }
    }

    interface Listener {
        fun onClick(option: UserOption)
    }
}
