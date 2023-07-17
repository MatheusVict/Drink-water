package com.example.drinkwater

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.drinkwater.databinding.ActivityMainBinding
import com.example.drinkwater.model.DailyIntakeCalculator
import com.example.drinkwater.model.interfacesDrink.CalculateDailyIntake
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dailyIntakeCalculator: DailyIntakeCalculator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        dailyIntakeCalculator = CalculateDailyIntake()

        binding.btnCalculate.setOnClickListener {
            validInputs()
        }

        binding.addRemember.setOnClickListener {
            chooseRemainderTime()
        }

        binding.alert.setOnClickListener {
            setAlarm()
        }

        binding.refreshButton.setOnClickListener { showDialog() }
    }

    private fun setupView() {
        window.statusBarColor = Color.parseColor("#FF0099CC")
    }

    private fun validInputs() {
        val weightInputText = binding.weightInput.text.toString()
        val ageInputText = binding.ageInput.text.toString()
        val toastWeightEmptyInput = R.string.toast_empty_weight
        val toastAgeEmptyInput = R.string.toast_empty_age

        if (weightInputText.isEmpty()) {
            showToast(toastWeightEmptyInput)
            return
        }
        if (ageInputText.isEmpty()) {
            showToast(toastAgeEmptyInput)
            return
        }
        val age = ageInputText.toInt()
        val weight = weightInputText.toDouble()
        val resultML = dailyIntakeCalculator.calculateTotalMl(weight, age)

        val formatter = NumberFormat.getNumberInstance(Locale("pt", "BR"))
        formatter.isGroupingUsed = false
        binding.txtResultMl.text = "${formatter.format(resultML)} ml"

    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton("OK") { _, _ ->
                binding.weightInput.setText("")
                binding.ageInput.setText("")
                binding.txtResultMl.text = ""
            }
            .setNegativeButton("Cancelar") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        val dialog = alertDialog.create()
        dialog.show()
    }

    private fun chooseRemainderTime() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { timePicker, hourOfDay, minute ->
                binding.txtHour.text = String.format("%02d", hourOfDay)
                binding.txtMinutes.text = String.format("%02d", minute)
            },
            currentHour,
            currentMinute,
            true
        )

        timePickerDialog.show()
    }

    private fun setAlarm() {
        val inputHourText = binding.txtHour.text.toString()
        val inputMinuteText = binding.txtMinutes.text.toString()

        if (inputHourText.isNotEmpty() && inputMinuteText.isNotEmpty()) {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, inputHourText.toInt())
                putExtra(AlarmClock.EXTRA_MINUTES, inputMinuteText.toInt())
                putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_message))
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

}