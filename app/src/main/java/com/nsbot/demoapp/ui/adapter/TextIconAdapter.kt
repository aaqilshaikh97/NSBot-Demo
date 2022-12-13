package com.nsbot.demoapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nsbot.demoapp.databinding.ItemSettingsBinding
import com.nsbot.demoapp.databinding.ItemTimingBinding
import com.nsbot.demoapp.models.TextIcon
import com.nsbot.demoapp.models.Timing

class TextIconAdapter(private val list: ArrayList<TextIcon>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<TextIconAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSettingsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val option = list[position]
        holder.binding.icon.setImageResource(option.icon)
        holder.binding.name.text = option.name

        holder.binding.root.setOnClickListener {
            onItemClickListener.onClick(option)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(textIcon: TextIcon)
    }

}