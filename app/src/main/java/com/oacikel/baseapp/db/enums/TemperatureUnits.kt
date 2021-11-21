package com.oacikel.baseapp.db.enums

enum class TemperatureUnits (var unit: String) {
    FAHRENHEIT("imperial"),
    CELSIUS("metric"),
    KELVIN("standard")
}