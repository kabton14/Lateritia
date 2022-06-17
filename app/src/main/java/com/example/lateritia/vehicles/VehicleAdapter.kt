package com.example.lateritia.vehicles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.R
import com.example.lateritia.database.Vehicle

class VehicleAdapter: RecyclerView.Adapter<VehicleAdapter.ViewHolder>(){
    var data = listOf<Vehicle>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_vehicle, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.vehicleName.text = "${item.make} ${item.model}"
        holder.vehicleName.isSelected = true
        holder.licence.text = "${item.licence}"
        holder.capacity.text = "${item.fuelCapacity}L"
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleName: TextView = itemView.findViewById(R.id.vehicle_item_title)
        val licence: TextView = itemView.findViewById(R.id.vehicle_item_info_1)
        val capacity: TextView = itemView.findViewById(R.id.vehicle_item_info_2)
    }
}