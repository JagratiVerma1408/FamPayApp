package com.example.fampayapp.CardViews

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fampayapp.Model.Card
import com.example.fampayapp.R

class HC6RecylerAdapter : RecyclerView.Adapter<HC6RecylerAdapter.MyViewHolder>()  {

    lateinit var context: Context
    lateinit var cards: List<Card>

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.hc6_tv)
        val iconIv: ImageView = itemView.findViewById(R.id.small_card_with_arrow_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hc6, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card = cards.get(position)

        holder.titleTv.setText(card.title)

        if(card.icon != null)
            Glide.with(context)
                .load(card.icon.image_url)
                .into(holder.iconIv)

        if(card.url != null){
            holder.itemView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(card.url))
                context.startActivity(browserIntent)
            }
        }
    }
}