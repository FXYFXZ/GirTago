package ru.fxy7ci.girtago.AstroLib

typealias TJD = Double

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
}

public class ln_date  {
    public var years: Short = 0     /*!<  1-99 */
    public var months: Byte = 0    /*!< Months. Valid values : 1 (January) - 12 (December) */
    public var days: Byte = 0      /*!< Days. Valid values 1 - 28,29,30,31 Depends on month.*/
    public var hours: Byte = 0     /*!< Hours. Valid values 0 - 23. */
    public var minutes: Byte = 0   /*!< Minutes. Valid values 0 - 59. */
    public var seconds: Byte = 0   /*!< Seconds. Valid values 0 - 59*/
}