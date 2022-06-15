package com.example.lateritia.vehicles

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lateritia.database.Vehicle

class VehicleAdapter: RecyclerView.Adapter<VehicleAdapter.ViewHolder>(){
    var data = listOf<Vehicle>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}