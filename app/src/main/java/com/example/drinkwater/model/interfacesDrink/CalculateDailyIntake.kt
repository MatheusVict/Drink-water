package com.example.drinkwater.model.interfacesDrink

import com.example.drinkwater.model.DailyIntakeCalculator


class CalculateDailyIntake : DailyIntakeCalculator {
    private val mlYoung: Double = 40.0
    private val mlAdult: Double = 35.0
    private val mlOld: Double = 30.0
    private val mlOver66Years: Double = 25.0

    override fun calculateTotalMl(weight: Double, age: Int): Double {
        val resultMl: Double = when {
            age <= 17 -> weight * mlYoung
            age <= 55 -> weight * mlAdult
            age <= 65 -> weight * mlOld
            else -> weight * mlOver66Years
        }
        return resultMl
    }
}
