package ru.fxy7ci.girtago.astroLib

typealias TJD = Double
typealias TJD2K = Double
typealias TCoords = Double

// В настройки потом...
val ECHO_JMT = 0.125   // коррекция на время по гринвичу + 3 / 24
val ECHO_LAT = 55.433  // текущая широта
val ECHO_LNG = 37.552  // текущая долгота

val J2000 =  2451545  // Юлианский день 2000 г.


public class StarTimes {
    public var isSet: Boolean = false
    var Noon : TJD = 0.0 // Время полудня
    var SunRise : TJD = 0.0 //  время восхода солнца
    var SunSet : TJD = 0.0 //  время захода солнца
    var moonState = TMoonState.SMN_ALWSUP
    var moonRise: TJD = 0.0 // время восхода луны
    var moonSet: TJD = 0.0  // время захода луны
    var MoonFraction = 0f
    var MoonPhase = 0f// Фаза луны
    var MoonAngle = 0f
}

public class ln_date  {
    public var years: Short = 0     /*!<  1-99 */
    public var months: Byte = 0    /*!< Months. Valid values : 1 (January) - 12 (December) */
    public var days: Byte = 0      /*!< Days. Valid values 1 - 28,29,30,31 Depends on month.*/
    public var hours: Byte = 0     /*!< Hours. Valid values 0 - 23. */
    public var minutes: Byte = 0   /*!< Minutes. Valid values 0 - 59. */
    public var seconds: Byte = 0   /*!< Seconds. Valid values 0 - 59*/
}

public class TObjCoords {
    public var ra: Double = 0.0
    public var dec: Double = 0.0
    public var dist: Double = 0.0
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