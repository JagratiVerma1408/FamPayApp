package com.example.fampayapp.CardViews

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fampayapp.Model.Card
import com.example.fampayapp.R


class HC9RecyclerAdapter : RecyclerView.Adapter<HC9RecyclerAdapter.MyViewHolder>()

{
    lateinit var context:Context
    lateinit var cards:List<Card>
    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
       val imageView : ImageView = itemView.findViewById(R.id.dynamic_width_card_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hc9,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val card = cards.get(position)
        Glide.with(context)
            .load(card.bg_image.image_url)
            .into(holder.imageView)
        if(card.url!=null)
        {
           holder.itemView.setOnClickListener {
               val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(card.url))
               context.startActivity(browserIntent)
           }
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }


}