package com.example.fampayapp.CardViews

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fampayapp.Model.Card
import com.example.fampayapp.Model.Cta
import com.example.fampayapp.R


class  HC3RecylerAdapter: RecyclerView.Adapter<HC3RecylerAdapter.MyViewHolder>() {

    lateinit var context: Context
    lateinit var cards: List<Card>
    lateinit var listener: onOptionsSelectListener

    interface onOptionsSelectListener{
         fun onRemindLaterSelected()
         fun onDismissNowSelected()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconIv : ImageView = itemView.findViewById(R.id.hc3_icon)
        val bigTv : TextView = itemView.findViewById(R.id.hc3_big_tv)
        val smallTv : TextView = itemView.findViewById(R.id.hc3_small_tv)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.hc3_linear_layout)
        val optionsLayout: LinearLayout = itemView.findViewById(R.id.hc3_options_layout)
        val remindLater: LinearLayout = itemView.findViewById(R.id.hc3_remind_later_layout)
        val dismissLayout: LinearLayout = itemView.findViewById(R.id.hc3_dismiss_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.hc3, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card: Card = cards.get(position)

        holder.bigTv.setText(card.title)
        holder.smallTv.setText(card.formatted_description.text)

        if(card.icon != null)
            Glide.with(context)
                .load(card.icon.image_url)
                .into(holder.iconIv)

        for(btn in card.cta){
            holder.linearLayout.addView(getCtaButton(btn))
        }

        if(card.url != null){
            holder.itemView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(card.url))
                context.startActivity(browserIntent)
            }
        }

        holder.itemView.setOnLongClickListener {
            if(holder.optionsLayout.visibility == View.VISIBLE){
                holder.optionsLayout.visibility = View.GONE
            }
            else
                holder.optionsLayout.visibility = View.VISIBLE

            return@setOnLongClickListener true
        }

        holder.dismissLayout.setOnClickListener {
            listener.onDismissNowSelected()
        }

        holder.remindLater.setOnClickListener {
            listener.onRemindLaterSelected()
        }
    }

    fun getCtaButton(cta: Cta) : Button{
        val button = Button(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(50, 50, 0, 50)

        button.layoutParams = params

        button.background = context.getDrawable(R.drawable.button_background)
        button.setTextColor(context.resources.getColor(android.R.color.white))
        button.text =cta.text
        button.setOnClickListener {
            if(cta.url != null){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(cta.url))
                context.startActivity(browserIntent)
            }
        }

        return button
    }
}