package com.example.lateritia.vehicles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.database.Vehicle
import com.example.lateritia.databinding.ListItemVehicleBinding

class VehicleAdapter(val current: Long ):
    ListAdapter<Vehicle, VehicleAdapter.ViewHolder>(VehicleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(val binding: ListItemVehicleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Vehicle) {
            binding.vehicle = item
            binding.vehicleItemTitle.isSelected = true
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemVehicleBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class VehicleDiffCallback : DiffUtil.ItemCallback<Vehicle>() {
    override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
        return oldItem == newItem
    }
}

class VehicleListener(val clickListener: (vehicleId: Long) -> Unit) {
    fun onClick(vehicle: Vehicle) = clickListener(vehicle.id)

}