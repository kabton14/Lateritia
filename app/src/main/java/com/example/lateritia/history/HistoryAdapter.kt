package com.example.lateritia.history

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
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

class HistoryAdapter(
    private val onLongClick: (FuelEntry) -> Unit,
    private val onLocationEdit: (FuelEntry) -> Unit
) :
    ListAdapter<FuelEntry, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry, onLocationEdit)
        holder.itemView.setOnLongClickListener {
            onLongClick(entry)
            true
        }
    }

    class ViewHolder private constructor(private val binding: ListItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: FuelEntry, onLocationEdit: (FuelEntry) -> Unit) {
            val ctx = binding.root.context
            binding.historyVehicleModel.text = entry.vehicleModel
            binding.historyDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                .format(Date(entry.timestamp))
            if (entry.lat != null && entry.lng != null) {
                binding.historyLocation.text = "%.4f°, %.4f°".format(entry.lat, entry.lng)
                binding.historyLocation.paintFlags =
                    binding.historyLocation.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                binding.historyLocation.setOnClickListener {
                    val uri = Uri.parse("geo:${entry.lat},${entry.lng}?q=${entry.lat},${entry.lng}")
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
                binding.historyLocation.setOnLongClickListener {
                    onLocationEdit(entry)
                    true
                }
            } else {
                binding.historyLocation.text = ctx.getString(R.string.location_unavailable)
                binding.historyLocation.paintFlags =
                    binding.historyLocation.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
                binding.historyLocation.setOnClickListener(null)
                binding.historyLocation.setOnLongClickListener(null)
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
