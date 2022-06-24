package com.example.lateritia.vehicles

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.lateritia.R
import com.example.lateritia.database.Vehicle

@BindingAdapter("vehicleImage")
fun ImageView.setVehicleImage(vehicle: Vehicle?) {
    setImageResource(when {
        vehicle?.model?.lowercase()?.contains("axio") == true -> R.drawable.drawing_axio_16_o
        else -> R.drawable.drawing_o
    })
}

@BindingAdapter("vehicle", "defaultVehicle")
fun ImageView.setDefaultIndicatorIcon(vehicle: Vehicle, defaultVehicle: Long) {
    setImageResource(
        when (vehicle.id) {
            defaultVehicle -> R.drawable.ic_default_tick
            else -> R.drawable.ic_hollow_circle
        }
    )
}