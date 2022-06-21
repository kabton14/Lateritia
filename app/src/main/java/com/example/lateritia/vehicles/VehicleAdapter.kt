package com.example.lateritia.vehicles

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.R
import com.example.lateritia.database.Vehicle
import com.example.lateritia.databinding.ListItemVehicleBinding

class VehicleAdapter(val current: Int ):
    ListAdapter<Vehicle, VehicleAdapter.ViewHolder>(VehicleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(val binding: ListItemVehicleBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail: ImageView = binding.vehicleItemImage
        val vehicleName: TextView = binding.vehicleItemTitle
        val licence: TextView = binding.vehicleItemInfo1
        val capacity: TextView = binding.vehicleItemInfo2
        val default: ImageView = binding.defaultVehicleIndicator

        fun bind(item: Vehicle) {
            vehicleName.text = "${item.make} ${item.model}"
            vehicleName.isSelected = true
            licence.text = "${item.licence}"
            capacity.text = "${item.fuelCapacity}L"

//            if (item.id == current.toLong()) {
//                holder.default.setImageResource(R.drawable.ic_default_tick)
//            } else {
//                holder.default.setImageResource(R.drawable.ic_hollow_circle)
//            }
            binding.vehicle = item
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

class VehicleListener(val onClickListener: (vehicleId: Long) -> Unit) {
    fun onClick(vehicle: Long) = onClickListener(vehicle)

}