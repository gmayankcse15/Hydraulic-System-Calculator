package com.uniquespm.hydraulic.util

import android.util.Log

class ConversionUtil {
    companion object {
        const val TAG = "ConversionUtil"
        val lengthBaseUnit: LENGTH = LENGTH.MM
        val pressureBaseUnit: PRESSURE = PRESSURE.BAR
        val areaBaseUnit: AREA = AREA.MM2
        val volumeBaseUnit: VOLUME = VOLUME.LITRE
        val forceBaseUnit: FORCE = FORCE.TON

//        fun convertUnitToBase(value: Double, unit: LENGTH): Double {
//            return when (unit) {
//                LENGTH.MM -> value * LENGTH.MM.factor
//                LENGTH.CM -> value * LENGTH.CM.factor
//                LENGTH.FEET -> value * LENGTH.FEET.factor
//                LENGTH.INCH -> value * LENGTH.INCH.factor
//                LENGTH.METER -> value * LENGTH.METER.factor
//            }
//        }
//
//        fun convertBaseToUnit(value: Double, unit: LENGTH): Double {
//            return when (unit) {
//                LENGTH.MM -> value / LENGTH.MM.factor
//                LENGTH.CM -> value / LENGTH.CM.factor
//                LENGTH.FEET -> value / LENGTH.FEET.factor
//                LENGTH.INCH -> value / LENGTH.INCH.factor
//                LENGTH.METER -> value / LENGTH.METER.factor
//            }
//        }

        fun convertUnit(fromValue: Double, fromUnit: UNIT, toUnit: UNIT): Double {
            Log.d(TAG, "convertUnit $fromValue ${fromUnit.unit} ${toUnit.unit}")
            val tmpValue = convertUnitToBase(fromValue, fromUnit)
            return convertBaseToUnit(tmpValue, toUnit)
        }

//        fun convertUnitToBase(value: Double, unit: PRESSURE): Double {
//            return when (unit) {
//                PRESSURE.BAR -> value * PRESSURE.BAR.factor
//                PRESSURE.ATM -> value * PRESSURE.ATM.factor
//                PRESSURE.KGCM2 -> value * PRESSURE.KGCM2.factor
//                PRESSURE.PASCAL -> value * PRESSURE.PASCAL.factor
//                PRESSURE.PSI -> value * PRESSURE.PSI.factor
//            }
//        }
//
//        fun convertBaseToUnit(value: Double, unit: PRESSURE): Double {
//            return when (unit) {
//                PRESSURE.BAR -> value / PRESSURE.BAR.factor
//                PRESSURE.ATM -> value / PRESSURE.ATM.factor
//                PRESSURE.KGCM2 -> value / PRESSURE.KGCM2.factor
//                PRESSURE.PASCAL -> value / PRESSURE.PASCAL.factor
//                PRESSURE.PSI -> value / PRESSURE.PSI.factor
//            }
//        }
//
//        fun convertUnit(fromValue: Double, fromUnit: PRESSURE, toUnit: PRESSURE): Double {
//            val tmpValue = convertUnitToBase(fromValue, fromUnit)
//            return convertBaseToUnit(tmpValue, toUnit)
//        }
//
//        fun convertUnitToBase(value: Double, unit: FORCE): Double {
//            return when (unit) {
//                FORCE.TON -> value * FORCE.TON.factor
//                FORCE.KGF -> value * FORCE.KGF.factor
//                FORCE.NEWTON -> value * FORCE.NEWTON.factor
//                FORCE.POUND -> value * FORCE.POUND.factor
//            }
//        }
//
//        fun convertBaseToUnit(value: Double, unit: FORCE): Double {
//            return when (unit) {
//                FORCE.TON -> value / FORCE.TON.factor
//                FORCE.KGF -> value / FORCE.KGF.factor
//                FORCE.NEWTON -> value / FORCE.NEWTON.factor
//                FORCE.POUND -> value / FORCE.POUND.factor
//            }
//        }
//
//        fun convertUnit(fromValue: Double, fromUnit: FORCE, toUnit: FORCE): Double {
//            val tmpValue = convertUnitToBase(fromValue, fromUnit)
//            return convertBaseToUnit(tmpValue, toUnit)
//        }
//
//        fun convertUnitToBase(value: Double, unit: VOLUME): Double {
//            return when (unit) {
//                VOLUME.LITRE -> value * VOLUME.LITRE.factor
//                VOLUME.MM3 -> value * VOLUME.MM3.factor
//                VOLUME.CM3 -> value * VOLUME.CM3.factor
//                VOLUME.ML -> value * VOLUME.ML.factor
//                VOLUME.UKGallon -> value * VOLUME.UKGallon.factor
//                VOLUME.USGallon -> value * VOLUME.USGallon.factor
//            }
//        }
//
//        fun convertBaseToUnit(value: Double, unit: VOLUME): Double {
//            return when (unit) {
//                VOLUME.LITRE -> value / VOLUME.LITRE.factor
//                VOLUME.MM3 -> value / VOLUME.MM3.factor
//                VOLUME.CM3 -> value / VOLUME.CM3.factor
//                VOLUME.ML -> value / VOLUME.ML.factor
//                VOLUME.UKGallon -> value / VOLUME.UKGallon.factor
//                VOLUME.USGallon -> value / VOLUME.USGallon.factor
//            }
//        }
//
//        fun convertUnit(fromValue: Double, fromUnit: VOLUME, toUnit: VOLUME): Double {
//            val tmpValue = convertUnitToBase(fromValue, fromUnit)
//            return convertBaseToUnit(tmpValue, toUnit)
//        }
//
        fun convertUnitToBase(value: Double, unit: UNIT): Double {
        return value * unit.factor
        }
//        }

        fun convertBaseToUnit(value: Double, unit: UNIT): Double {
             return value / unit.factor
        }

//        fun convertUnit(fromValue: Double, fromUnit: AREA, toUnit: AREA): Double {
//            val tmpValue = convertUnitToBase(fromValue, fromUnit)
//            return convertBaseToUnit(tmpValue, toUnit)
//        }

    }
}

interface UNIT {
    val unit: String
    val factor: Double
}

enum class LENGTH : UNIT {
    MM {
        override val unit: String
            get() = "mm"
        override val factor: Double
            get() = 1.0
    },
    CM {
        override val unit: String
            get() = "cm"
        override val factor: Double
            get() = 10.0
    },
    METER {
        override val unit: String
            get() = "meter"
        override val factor: Double
            get() = 1000.0
    },
    INCH {
        override val unit: String
            get() = "inch"
        override val factor: Double
            get() = 25.4
    },
    FEET {
        override val unit: String
            get() = "feet"
        override val factor: Double
            get() = 304.8
    }
}

enum class VOLUME : UNIT {
    LITRE {
        override val unit: String
            get() = "litre"
        override val factor: Double
            get() = 1.0
    },
    ML {
        override val unit: String
            get() = "ml"
        override val factor: Double
            get() = 0.001
    },
    MM3 {
        override val unit: String
            get() = "mm\u00b3"
        override val factor: Double
            get() = 0.000001
    },
    CM3 {
        override val unit: String
            get() = "cm\u00b3"
        override val factor: Double
            get() = 0.001
    },
    USGallon {
        override val unit: String
            get() = "US Gallon"
        override val factor: Double
            get() = 3.785
    },
    UKGallon {
        override val unit: String
            get() = "UK Gallon"
        override val factor: Double
            get() = 4.546
    }
}
//
//enum class MASS(val unit: String) : UNIT {
//    KG("kg"),
//    GRAM("gram"),
//    TONS("tons"),
//    MG("mg"),
//    POUND("pound")
//}
//
enum class PRESSURE : UNIT {
    BAR {
        override val unit: String
            get() = "bar"
        override val factor: Double
            get() = 1.0
    },
    PASCAL {
        override val unit: String
            get() = "pascal"
        override val factor: Double
            get() = 0.00001
    },
    PSI {
        override val unit: String
            get() = "psi"
        override val factor: Double
            get() = 1.0 / 14.5
    },
    ATM {
        override val unit: String
            get() = "atm"
        override val factor: Double
            get() = 1.0 / 1.01325
    },
    KGCM2 {
        override val unit: String
            get() = "kg/cm\u00b2"
        override val factor: Double
            get() = 1 / 1.01972
    }
}

enum class FORCE : UNIT {
    TON {
        override val unit: String
            get() = "ton"
        override val factor: Double
            get() = 1.0
    },
    NEWTON {
        override val unit: String
            get() = "newton"
        override val factor: Double
            get() = 1.0 / 9806.65
    },
    KGF {
        override val unit: String
            get() = "kgf"
        override val factor: Double
            get() = 0.001
    },
    POUND{
        override val unit: String
            get() = "pound"
        override val factor: Double
            get() = 1.0 / 2204.6
    }
}

//enum class TEMPERATURE(val unit: String) : UNIT {
//    CO("C\u00b0"),
//    FO("F\u00b0"),
//    KO("K\u00b0")
//}
//
//enum class TIME(val unit: String) : UNIT {
//    SEC("sec"),
//    MIN("min"),
//    HOUR("hour")
//}
//
//enum class SPEED(val unit: String) : UNIT {
//    MM_SEC("mm/sec"),
//    M_SEC("m/sec"),
//    M_MIN("m/min")
//}
//
enum class AREA : UNIT {
    M2 {
        override val unit: String
            get() = "m\u00b2"
        override val factor: Double
            get() = 1.0
    },
    MM2 {
        override val unit: String
            get() = "mm\u00b2"
        override val factor: Double
            get() = 0.000001
    },
    CM2 {
        override val unit: String
            get() = "cm\u00b2"
        override val factor: Double
            get() = 0.0001
    },
    FT2 {
        override val unit: String
            get() = "ft\u00b2"
        override val factor: Double
            get() = 1.0 / 10.764
    },
    INCH2 {
        override val unit: String
            get() = "inch\u00b2"
        override val factor: Double
            get() = 1.0 / 1550.0
    }
}

//enum class ENERGY(val unit: String) : UNIT {
//    HP("H.P."),
//    WATT("watt"),
//    KW("kw"),
//    W("w"),
//    Calorie("calorie")
//}

//enum class FLOW(val unit: String) : UNIT {
//    L_MIN("l/min"),
//    M3_MIN("m\u00b3/min"),
//    FPM("fpm")
//}
//
//enum class ANGLE(val unit: String) : UNIT {
//    RADIAN("radian"),
//    DEGREE("degree"),
//    MINUTE("minute"),
//    SECOND("second")
//}

//fun main () {
//    val d = ConversionUtil.convertUnit(1.0, LENGTH.METER, LENGTH.CM)
//    print("value $d")
//}