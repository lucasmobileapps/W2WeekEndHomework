package com.example.w2weekendhomework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.w2weekendhomework.R
import com.example.w2weekendhomework.util.ColorObject

class ColorAdapter(private val colors: List<String>, private val delegator: colorAdapterDelegator) : RecyclerView.Adapter<ColorAdapter.CustomViewHolder>(){

    lateinit var appContext: Context

    interface colorAdapterDelegator{
        fun colorPicked(colorResource: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_item_view_layout, parent, false)
        appContext = parent.context.applicationContext
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.colorText.text = colors[position]
        when(colors[position])
        {
            ColorObject.COLOR_BLUE -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(appContext, R.color.appBlue))
                holder.itemView.setOnClickListener {
                    delegator.colorPicked(ColorObject.COLOR_BLUE)
                }
            }

            ColorObject.COLOR_RED -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(appContext, R.color.appRed))
                holder.itemView.setOnClickListener {
                    delegator.colorPicked(ColorObject.COLOR_RED)
                }
            }

            ColorObject.COLOR_GREEN -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(appContext, R.color.appGreen))
                holder.itemView.setOnClickListener {
                    delegator.colorPicked(ColorObject.COLOR_GREEN)
                }
            }
        }
    }

    class CustomViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val colorText: TextView = itemView.findViewById(R.id.color_textiew)
    }
}