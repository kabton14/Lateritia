package com.example.lateritia.vehicles

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.lateritia.R
import com.example.lateritia.database.Vehicle

@BindingAdapter("vehicleImage")
fun ImageView.setVehicleImage(vehicle: Vehicle?) {
    setImageResource(when {
        vehicle?.model?.lowercase()?.contains("axio") == true -> R.drawable.drawing_axio_16_o
        vehicle?.model?.lowercase()?.contains("rush") == true -> R.drawable.drawing_o
        vehicle?.model?.lowercase()?.contains("impreza") == true -> R.drawable.drawing_impreza_hatch_17
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

@BindingAdapter("vehicle")
fun ImageView.setDefaultIndicatorIcon(vehicle: Vehicle) {
    setImageResource(R.drawable.ic_default_tick)
}

@BindingAdapter("vehicle", "level")
fun ImageView.setFuelGauge(vehicle: Vehicle, level: Int) {
    setImageResource(
        when {
            vehicle?.model?.lowercase()?.contains("axio") == true -> {
                when (level) {
                    0 -> R.drawable.ic_fuel_gauge_axio_16_0
                    1 -> R.drawable.ic_fuel_gauge_axio_16_1
                    2 -> R.drawable.ic_fuel_gauge_axio_16_2
                    3 -> R.drawable.ic_fuel_gauge_axio_16_3
                    4 -> R.drawable.ic_fuel_gauge_axio_16_4
                    5 -> R.drawable.ic_fuel_gauge_axio_16_5
                    6 -> R.drawable.ic_fuel_gauge_axio_16_6
                    7 -> R.drawable.ic_fuel_gauge_axio_16_7
                    8 -> R.drawable.ic_fuel_gauge_axio_16_8
                    else -> R.drawable.ic_minus_dec
                }
            }
            vehicle?.model?.lowercase()?.contains("rush") == true -> {
                when (level) {
                    0 -> R.drawable.ic_fuel_gauge_0
                    1 -> R.drawable.ic_fuel_gauge_1
                    2 -> R.drawable.ic_fuel_gauge_2
                    3 -> R.drawable.ic_fuel_gauge_3
                    4 -> R.drawable.ic_fuel_gauge_4
                    5 -> R.drawable.ic_fuel_gauge_5
                    6 -> R.drawable.ic_fuel_gauge_6
                    7 -> R.drawable.ic_fuel_gauge_7
                    8 -> R.drawable.ic_fuel_gauge_8
                    else -> R.drawable.ic_minus_dec
                }
            }
            vehicle?.model?.lowercase()?.contains("impreza") == true -> {
                when (level) {
                    0 -> R.drawable.ic_fuel_gauge_impreza_17_0
                    1 -> R.drawable.ic_fuel_gauge_impreza_17_1
                    2 -> R.drawable.ic_fuel_gauge_impreza_17_2
                    3 -> R.drawable.ic_fuel_gauge_impreza_17_3
                    4 -> R.drawable.ic_fuel_gauge_impreza_17_4
                    5 -> R.drawable.ic_fuel_gauge_impreza_17_5
                    6 -> R.drawable.ic_fuel_gauge_impreza_17_6
                    7 -> R.drawable.ic_fuel_gauge_impreza_17_7
                    8 -> R.drawable.ic_fuel_gauge_impreza_17_8
                    9 -> R.drawable.ic_fuel_gauge_impreza_17_9
                    10 -> R.drawable.ic_fuel_gauge_impreza_17_10
                    11 -> R.drawable.ic_fuel_gauge_impreza_17_11
                    12 -> R.drawable.ic_fuel_gauge_impreza_17_12
                    else -> R.drawable.ic_minus_dec
                }
            }
            else -> {
                when (level) {
                    0 -> R.drawable.ic_fuel_gauge_0
                    1 -> R.drawable.ic_fuel_gauge_1
                    2 -> R.drawable.ic_fuel_gauge_2
                    3 -> R.drawable.ic_fuel_gauge_3
                    4 -> R.drawable.ic_fuel_gauge_4
                    5 -> R.drawable.ic_fuel_gauge_5
                    6 -> R.drawable.ic_fuel_gauge_6
                    7 -> R.drawable.ic_fuel_gauge_7
                    8 -> R.drawable.ic_fuel_gauge_8
                    else -> R.drawable.ic_minus_dec
                }
            }
        }
    )
}
