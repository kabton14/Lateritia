package com.example.lateritia.vehicles

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.R
import com.example.lateritia.database.Vehicle

class VehicleAdapter: RecyclerView.Adapter<VehicleAdapter.ViewHolder>(){
    var data = listOf<Vehicle>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleName: TextView = itemView.findViewById(R.id.vehicle_item_title)
        val licence: TextView = itemView.findViewById(R.id.vehicle_item_info_1)
        val capacity: TextView = itemView.findViewById(R.id.vehicle_item_info_2)
    }
}