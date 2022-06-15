package com.example.lateritia.vehicles

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.R
import com.example.lateritia.database.Vehicle

class VehicleAdapter: RecyclerView.Adapter<VehicleAdapter.ViewHolder>(){
    var data = listOf<Vehicle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleName: TextView = itemView.findViewById(R.id.vehicle_item_title)
        val licence: TextView = itemView.findViewById(R.id.vehicle_item_info_1)
        val capacity: TextView = itemView.findViewById(R.id.vehicle_item_info_2)
    }
}