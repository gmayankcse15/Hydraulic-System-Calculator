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

        fun convertUnit(fromValue: Double, fromUnit: UNIT, toUnit: UNIT): Double {
            Log.d(TAG, "convertUnit $fromValue ${fromUnit.unit} ${toUnit.unit}")
            val tmpValue = convertUnitToBase(fromValue, fromUnit)
            return convertBaseToUnit(tmpValue, toUnit)
        }

        fun convertUnitToBase(value: Double, unit: UNIT): Double {
        return value * unit.factor
        }

        fun convertBaseToUnit(value: Double, unit: UNIT): Double {
             return value / unit.factor
        }
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
    M3 {
        override val unit: String
            get() = "m\u00b3"
        override val factor: Double
            get() = 1000.0
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
enum class SPEED: UNIT {
    MM_SEC {
        override val unit: String
            get() = "mm/sec"
        override val factor: Double
            get() = 1.0
    },
    M_SEC {
        override val unit: String
            get() = "m/sec"
        override val factor: Double
            get() =  1000.0
    },
    M_MIN {
        override val unit: String
            get() = "m/min"
        override val factor: Double
            get() = 1.0/0.06
    },
    MM_MIN {
        override val unit: String
        get() = "mm/min"
        override val factor: Double
        get() = 1.0/60.0
    }
}

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

enum class ENERGY : UNIT {
    HP {
        override val unit: String
            get() = "hp"
        override val factor: Double
            get() = 746.0
    },
    WATT {
        override val unit: String
            get() = "watt"
        override val factor: Double
            get() = 1.0
    },
    KW {
        override val unit: String
            get() = "kw"
        override val factor: Double
            get() =  1000.0

    },
    Calorie {
        override val unit: String
            get() = "Calorie"
        override val factor: Double
            get() = 4.18
    }
}

enum class FLOW : UNIT {
    L_MIN {
        override val unit: String
            get() = "l/min"
        override val factor: Double
            get() = 1.0
    },
    M3_MIN {
        override val unit: String
            get() =  "m\u00b3/min"
        override val factor: Double
            get() = 1000.0
    },
    FPM {
        override val unit: String
            get() = "fpm"
        override val factor: Double
            get() =  3.785
    }
}
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