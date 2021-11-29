package ru.fxy7ci.girtago

import ru.fxy7ci.girtago.AstroLib.*
import kotlin.math.*

// Расчет событий восхода и захода солнца и луны
typealias TJD2K = Double
typealias TCoords = Double

private val D2R = (1.7453292519943295769e-2)  /* deg->radian */
private val DM_PI = (2*PI)
private val RADIAN = (180.0 / PI)

private val  J0 = 0.0009
private val E_O = (D2R * 23.4397)  // Наклон земной оси


// Subs

private fun ToDays(myDate: ln_date): TJD2K {
    return (ln_get_julian_day(myDate) - ECHO_JMT - J2000 );
}

private fun JulianCycle( d: Double,  lw:Double): Short {
    return round(d - J0 - lw/(2*PI)).toInt().toShort()
}

private fun approxTransit(Ht:Double, lw:Double, n: Short): Double {
    return J0 + (Ht+lw)/(2*PI) + n
}

private fun solarMeanAnomaly(j2k:Double): Double {
    return D2R * (357.5291 + 0.98560028 * j2k)
}
private fun eclipticLongitude(M:Double): Double {
    val C: Double =
        D2R * (1.9148 * sin(M) + 0.02 * sin(2 * M) + 0.0003 * sin(3 * M)) // equation of center
    val P = D2R * 102.9372 // perihelion of the Earth
    return M + C + P + PI
}

private fun declination(l:Double, b:Double): Double {
    return asin(sin(b) * cos(E_O) + cos(b) * sin(E_O) * sin(l))
}

private fun solarTransitJ(ds: Double, M: Double, L: Double): Double {
    return J2000 + ds + 0.0053 * sin(M) - 0.0069 * sin(2 * L)
}

private fun hourAngle(h: Double, phi: Double, d: Double): Double {
    var temp = (sin(h) - sin(phi) * sin(d)) / (cos(phi) * cos(d))
    if (abs(temp) > 1) {
        temp = if (temp > 0) 1.0 else -1.0
    }
    return acos(temp)
}

private fun getSetJ(h: Double, lw: Double, phi: Double, dec: Double,
                    M: Double, L: Double, n: Short): Double {
    val w: Double
    val a: Double
    w = hourAngle(h, phi, dec)
    a = approxTransit(w, lw, n)
    return solarTransitJ(a, M, L)
}






fun ln_get_julian_day(date: ln_date): TJD {
    val extra = 100.0 * date.years + date.months - 190002.5
    return 367.0 * date.years -
            Math.floor(7.0 * (date.years + Math.floor((date.months + 9.0) / 12.0)) / 4.0) +
            Math.floor(275.0 * date.months / 9.0) +
            date.days + (date.hours + (date.minutes + date.seconds / 60.0) / 60.0) / 24.0 +
            1721013.5 - 0.5 * extra / Math.abs(extra) + 0.5
}

