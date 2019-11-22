package com.example.urbandictionaryapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.data.model.Definition
import kotlinx.android.synthetic.main.definition_item.view.*
import kotlin.random.Random

class CustomRecyclerViewAdapter(var list: List<Definition>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemLayout = layoutInflater.inflate(
            R.layout.definition_item,
            parent,
            false) as LinearLayout
        return CustomViewHolder(itemLayout)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val current = list[position]
        holder.view.tvDefinition.text = current.definition
        holder.view.tvNumThumbUp.text = "thumbs up: " + current.thumbsUp.toString()
        holder.view.tvNumThumbDown.text = "thumbs down " + current.thumbsDown.toString()
        (holder.view as LinearLayout).setBackgroundColor(randomColor())
    }

    private fun randomColor() = Color.rgb(
        Random.nextInt(200) + 50,
        Random.nextInt(200) + 50,
        Random.nextInt(200) + 50
    )
}