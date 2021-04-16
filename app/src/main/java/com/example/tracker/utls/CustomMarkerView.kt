package com.example.tracker.utls

import android.content.Context
import android.view.LayoutInflater
import com.example.tracker.databinding.MarkerViewBinding
import com.example.tracker.db.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    private var binding: MarkerViewBinding =
        MarkerViewBinding.inflate(LayoutInflater.from(c), this, true)

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if (e == null)
            return

        val currentID = e.x.toInt()
        val run = runs[currentID]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timeInMillis
        }

        val dateFormant = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        binding.tvDate.text = dateFormant.format(calendar.time)

        val avgSpeed = "${run.averageSpeedInKMH}km/h"
        binding.tvAvgSpeed.text = avgSpeed

        val distanceInKm = "${run.distanceInMeters / 1000f}km"
        binding.tvDistance.text = distanceInKm

        binding.tvDuration.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned}kcal"
        binding.tvCaloriesBurned.text = caloriesBurned
    }
}