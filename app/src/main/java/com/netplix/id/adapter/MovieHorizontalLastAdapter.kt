package com.netplix.id.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.netplix.id.R
import com.netplix.id.connection.Host.URLIMAGE
import com.netplix.id.model.Result

class MovieHorizontalLastAdapter
    (
        private val onItemClicked: (position: Int) -> Unit
    ) :

    RecyclerView.Adapter<MovieHorizontalLastAdapter.ViewHolder>() {

    var items = ArrayList<Result>()
    lateinit var mContext: Context
    private val lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.list_item_film_horizontal, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Result = items[position]
        Glide.with(mContext)
            .load(URLIMAGE + data.poster_path)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image)
                    .transform(RoundedCorners(16))
            )
            .into(holder.imgPhoto)
//        holder.imgJudul.text = data.title!!
//        holder.imgDesciption.text = data.overview!!
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(itemView: View, position: Int) {
        if (position > lastPosition) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = FADE_DURATION.toLong()
            itemView.startAnimation(anim)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //Class Holder
    inner class ViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imgPhoto: ImageView
//        var imgJudul: TextView
//        var imgDesciption: TextView

        init {
            imgPhoto = itemView.findViewById(R.id.imgPhoto)
//            imgJudul = itemView.findViewById(R.id.imgJudul)
//            imgDesciption = itemView.findViewById(R.id.imgDesciption)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

    companion object {
        private const val FADE_DURATION = 1000 //FADE_DURATION in milliseconds
    }

    fun setDataList(data :  ArrayList<Result>) {
        this.items = data
        notifyDataSetChanged()
    }
}
