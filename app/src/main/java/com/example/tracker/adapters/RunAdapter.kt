package com.example.tracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tracker.databinding.ItemRunBinding
import com.example.tracker.db.Run
import com.example.tracker.utls.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : ListAdapter<Run, RunAdapter.RunViewHolder>(COMPARATOR){

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Run>() {
            override fun areItemsTheSame(oldItem: Run, newItem: Run) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Run, newItem: Run) =
                oldItem.hashCode() == newItem.hashCode()

        }
    }
    class RunViewHolder(private val binding: ItemRunBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup) : RunViewHolder {
                val binding = ItemRunBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RunViewHolder(binding)
            }
        }

        fun bind(item: Run) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.img)
                    .into(ivRunImage)

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = item.timestamp
                }
                val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                tvDate.text = dateFormat.format(calendar.time)

                val avgSpeed = "${item.averageSpeedInKMH}km/h"
                tvAvgSpeed.text = avgSpeed

                val distanceInKm = "${item.distanceInMeters / 1000f}km"
                tvDistance.text = distanceInKm

                tvTime.text = TrackingUtility.getFormattedStopWatchTime(item.timeInMillis)

                val caloriesBurned = "${item.caloriesBurned}kcal"
                tvCalories.text = caloriesBurned
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = getItem(position)
        holder.bind(run)
    }
}