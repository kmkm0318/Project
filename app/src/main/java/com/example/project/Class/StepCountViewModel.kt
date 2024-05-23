package com.example.project.Class

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class StepCountViewModel(context: Context): ViewModel(), SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounter: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private val _stepCountChannel = Channel<Int>(Channel.CONFLATED)
    val stepCountFlow = _stepCountChannel.receiveAsFlow()

    init {
        stepCounter?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.firstOrNull()?.let { stepCount->
            viewModelScope.launch {
                _stepCountChannel.send(stepCount.toInt())
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {  }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    fun hasSensor():Boolean{
        if (stepCounter == null){
            return false
        }
        else{
            return true
        }
    }

}