package com.nsbot.demoapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nsbot.demoapp.databinding.ItemTimingBinding
import com.nsbot.demoapp.models.Timing

class TimingAdapter(private val list: ArrayList<Timing>): RecyclerView.Adapter<TimingAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemTimingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTimingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val option = list[position]
        holder.binding.dayName.text = option.day

        if (option.open) {
            holder.binding.time.text = option.openAt+" to "+option.closeAt
        }else {
            holder.binding.time.text = "Closed"
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}