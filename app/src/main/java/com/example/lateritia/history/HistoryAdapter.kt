package com.example.lateritia.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.R
import com.example.lateritia.database.FuelEntry
import com.example.lateritia.databinding.ListItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter : ListAdapter<FuelEntry, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ListItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: FuelEntry) {
            val ctx = binding.root.context
            binding.historyVehicleModel.text = entry.vehicleModel
            binding.historyDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                .format(Date(entry.timestamp))
            binding.historyLocation.text = if (entry.lat != null && entry.lng != null) {
                "%.4f°, %.4f°".format(entry.lat, entry.lng)
            } else {
                ctx.getString(R.string.location_unavailable)
            }
            binding.historyPrice.text = ctx.getString(R.string.history_price, entry.pricePerLiter)
            binding.historyTotalPaid.text = ctx.getString(R.string.history_total, entry.totalPaid)
            binding.historyPercent.text = ctx.getString(R.string.history_percent, entry.percentFilled)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ListItemHistoryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class HistoryDiffCallback : DiffUtil.ItemCallback<FuelEntry>() {
    override fun areItemsTheSame(oldItem: FuelEntry, newItem: FuelEntry) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: FuelEntry, newItem: FuelEntry) = oldItem == newItem
}
