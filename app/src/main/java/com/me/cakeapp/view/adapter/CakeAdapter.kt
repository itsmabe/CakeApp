package com.me.cakeapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.adapter_cake_item.view.*
import javax.inject.Inject
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.me.cakeapp.R
import com.me.cakeapp.model.Cake


class CakeAdapter @Inject constructor(@ActivityContext private val context: Context) :
    RecyclerView.Adapter<CakeAdapter.ViewHolder>() {

    private var cakes = listOf<Cake>()
    private var lastPosition = -1
    var onClickListener: ((cake: Cake) -> Unit?)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: AppCompatImageView = view.image
        val text: TextView = view.text
        val container: ConstraintLayout = view.container
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.adapter_cake_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cake = cakes[position]
        viewHolder.text.text = cake.title?.lowercase()?.capitalize()
        Glide.with(context)
            .load(cake.image)
            .placeholder(R.drawable.ic_error_24)
            .into(viewHolder.image)
        viewHolder.image.clipToOutline = true
        viewHolder.container.setOnClickListener { onClickListener?.invoke(cake) }
        // Applying an animation on items list (slide in left)
        animate(viewHolder.itemView, position)
    }

    fun updateCakes(cakes: List<Cake>) {
        this.cakes = cakes
        notifyDataSetChanged()
    }

    override fun getItemCount() = cakes.size

    private fun animate(view: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            animation.duration = 700L
            view.startAnimation(animation)
            lastPosition = position
        }
    }

}
