package ru.fxy7ci.girtago.astroLib.AstroControls

typealias TJD = Double
typealias TJD2K = Double
typealias TCoords = Double

// В настройки потом...
const val J2000 =  2451545  // Юлианский день 2000 г.

class StarTimes {
    var isSet: Boolean = false
    var JD : TJD = 0.0
    var noon : TJD = 0.0 // Время полудня
    var sunRise : TJD = 0.0 //  время восхода солнца
    var sunSet : TJD = 0.0 //  время захода солнца
    var moonState = TMoonState.SMN_ALWSUP
    var moonRise: TJD = 0.0 // время восхода луны
    var moonSet: TJD = 0.0  // время захода луны
    var moonFraction = 0f
    var moonPhase = 0f// Фаза луны
    var moonAngle = 0f
}

class ln_date  {
    var years: Short = 0     /*!<  1-99 */
    var months: Byte = 0    /*!< Months. Valid values : 1 (January) - 12 (December) */
    var days: Byte = 0      /*!< Days. Valid values 1 - 28,29,30,31 Depends on month.*/
    var hours: Byte = 0     /*!< Hours. Valid values 0 - 23. */
    var minutes: Byte = 0   /*!< Minutes. Valid values 0 - 59. */
    var seconds: Byte = 0   /*!< Seconds. Valid values 0 - 59*/
}

class TObjCoords {
    var ra: Double = 0.0
    var dec: Double = 0.0
    var dist: Double = 0.0
}

enum class TMoonState {
    SMN_UPDOWN, // UpDown восходит и заходит
    SMN_ALWSUP, // AlwaysUp не заходит
    SMN_ALWSDN  // AlwaysDown не восходит
}

enum class TSunTimes {
    SST_SET,    // sunset
    SST_STRT,   // sunsetStart
    SST_DUSK,   // dusk
    SST_NDUSK,  // nauticalDusk
    SST_NGT,    // night
    SST_GHR     // goldenHour
}