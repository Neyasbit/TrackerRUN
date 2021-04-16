package com.example.tracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tracker.R
import com.example.tracker.databinding.FragmentStatisticsBinding
import com.example.tracker.databinding.MarkerViewBinding
import com.example.tracker.utls.CustomMarkerView
import com.example.tracker.utls.TrackingUtility
import com.example.tracker.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private var _bindingMarker: MarkerViewBinding? = null
    private val bindingMarker get() = _bindingMarker!!

    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        _bindingMarker = MarkerViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setupBarChart()
    }

    private fun subscribeToObservers() {
        statisticsViewModel.totalTimeRun.observe(viewLifecycleOwner) {
            it?.let {
                val totalTime = TrackingUtility.getFormattedStopWatchTime(it)
                binding.tvTotalTime.text = totalTime
            }
        }
        statisticsViewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "${totalDistance}km"
                binding.tvTotalDistance.text = totalDistanceString
            }
        }
        statisticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "${avgSpeed}km/h"
                binding.tvAverageSpeed.text = avgSpeedString
            }
        }
        statisticsViewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "${it}kcal"
                binding.tvTotalCalories.text = totalCalories
            }
        }
        statisticsViewModel.runsSortedByDate.observe(viewLifecycleOwner) {
            it?.let {
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(), it[i].averageSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeed, "Avg Speed Over Time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }
                binding.barChart.apply {
                    data = BarData(barDataSet)
                    marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                    invalidate()
                }
            }
        }
    }

    private fun setupBarChart() {
        binding.barChart.apply {
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawLabels(false)
                axisLineColor = Color.WHITE
                textColor = Color.WHITE
                setDrawGridLines(false)
            }
            axisLeft.apply {
                axisLineColor = Color.WHITE
                textColor = Color.WHITE
                setDrawGridLines(false)
            }
            axisRight.apply {
                axisLineColor = Color.WHITE
                textColor = Color.WHITE
                setDrawGridLines(false)
            }
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _bindingMarker = null
    }
}