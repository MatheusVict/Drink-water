package com.example.drinkwater

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.drinkwater.databinding.ActivityMainBinding
import com.example.drinkwater.model.DailyIntakeCalculator
import com.example.drinkwater.model.interfacesDrink.CalculateDailyIntake
import java.text.NumberFormat
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
}