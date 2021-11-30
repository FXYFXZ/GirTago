package ru.fxy7ci.girtago

import ru.fxy7ci.girtago.AstroLib.*
import kotlin.math.*
import android.R.bool

// Расчет событий восхода и захода солнца и луны

private val D2R = (1.7453292519943295769e-2)  /* deg->radian */
private val DM_PI = (2*PI)
private val RADIAN = (180.0 / PI)

private val  J0 = 0.0009
private val E_O = (D2R * 23.4397)  // Наклон земной оси
private val SUN_CONST = arrayOf(-0.833, -0.3, -0.6, -12, -18, 6)

// переменная главного расчета
public var theStarTimes = StarTimes()

fun AstroSetSunTimes(date: ln_date, lat: TCoords, lng: TCoords) {
    val lw: Double; val phi: Double; val ds: Double; val M: Double
    val L: Double;  val dec: Double; val n: Short;   val d: TJD2K

    lw  = D2R * -lng; phi = D2R * lat

    d  = ToDays(date)
    n  = JulianCycle(d, lw) // здесь теряем своё время, оставляем только дату
    ds = approxTransit(0.0, lw, n)
    M  = solarMeanAnomaly(ds)
    L  = eclipticLongitude(M)
    dec = declination(L, 0.0)

    theStarTimes.Noon = solarTransitJ(ds, M, L) // Полдень на юлианский день
    val h : Double = SUN_CONST[TSunTimes.SST_SET.ordinal] as Double
    theStarTimes.SunSet  = getSetJ(h * D2R, lw, phi, dec, M, L, n)
    theStarTimes.SunRise = theStarTimes.Noon - (theStarTimes.SunSet - theStarTimes.Noon)
}

fun AstroSetMoonTimes(date: ln_date, lat: TCoords, lng: TCoords){
    var hc: Double; var h0: Double; var h1: Double; var h2: Double
    var a: Double;  var b: Double;  var xe: Double; var ye: Double
    var d: Double;  var x1: Double =0.0; var x2: Double = 0.0; var dx: Double
    var mrise: Double =0.0;  var mset: Double =0.0
    var i: Int
    var roots: Int
    var t_mdn: TJD2K // Дата полуночи

    val ldate = ln_date()
    ldate.hours = 0
    ldate.minutes = 0
    ldate.seconds = 0
    t_mdn = ToDays(ldate)

    hc = 0.133 * D2R;
    h0 = GetMoonAltitude(t_mdn, lat, lng) - hc
    // go in 2-hour chunks, each time seeing if a 3-point quadratic curve crosses zero
    // (which means rise or set)
    theStarTimes.moonRise = 0.0
    theStarTimes.moonSet = 0.0

    i = 1
    var risefound : Boolean = false
    var setfound : Boolean  = false
    do {
        h1 = GetMoonAltitude(hoursLater(t_mdn, i.toDouble()), lat, lng) - hc
        h2 = GetMoonAltitude(hoursLater(t_mdn, (i+1).toDouble()), lat, lng) - hc
        a = (h0 + h2) / 2 - h1
        b = (h2 - h0) / 2

        xe = -b / (2 * a)
        ye = (a * xe + b) * xe + h1
        d  = b * b - 4 * a * h1
        roots = 0

        if (d >= 0){
            dx = sqrt(d) / (abs(a) * 2)
            x1 = xe - dx
            x2 = xe + dx
            if (abs(x1) <= 1) roots = roots + 1
            if (abs(x2) <= 1) roots = roots + 1
            if (x1 < -1) x1 = x2
        }
        if (roots == 1) {
            if (h0 < 0){
                mrise = i + x1
                risefound = true
            }
            else {
                mset = i + x1
                setfound = true
            }
        }
        else if (roots == 2) {
            if (ye < 0){
                mrise = i + x2
                mset  = i + x1
            }
            else {
                mrise = i + x1
                mset  = i + x2
            }
            risefound = true
            setfound = true
        }

        if (risefound and setfound)
            break

        h0 = h2
        i += 2

    } while (i <= 24)

    if (risefound)
        theStarTimes.moonRise = hoursLater(t_mdn, mrise)
    else
        theStarTimes.moonRise = 0.0

    if (setfound)
        theStarTimes.moonSet  = hoursLater(t_mdn, mset)
    else
        theStarTimes.moonSet  = 0.0

    if (risefound && setfound)
        theStarTimes.moonState = TMoonState.SMN_UPDOWN
    else {
        if (risefound || setfound) {
            if (risefound) 
                theStarTimes.moonState = TMoonState.SMN_ALWSUP
            else 
                theStarTimes.moonState = TMoonState.SMN_ALWSDN
        }
        else {
            if (ye > 0)
                theStarTimes.moonState = TMoonState.SMN_ALWSUP
            else
                theStarTimes.moonState = TMoonState.SMN_ALWSDN
        }
    }
}


fun AstroSetMoonIllumination(myDate: ln_date){
    val phi: Double
    val inc: Double
    val angle: Double
    val sdist = 149598000 // distance from Earth to Sun in km
    val tmpDate = ToDays(myDate)
    val sc = sunCoords(tmpDate)
    val mc = moonCoords(tmpDate)
    phi = acos(sin(sc.dec) * sin(mc.dec) + cos(sc.dec) * cos(mc.dec) * cos(sc.ra - mc.ra))
    inc = atan2(sdist * sin(phi), mc.dist - sdist * cos(phi));
    angle = atan2(cos(sc.dec) * sin(sc.ra - mc.ra), sin(sc.dec) * cos(mc.dec) -
            cos(sc.dec) * sin(mc.dec) * cos(sc.ra - mc.ra))
    theStarTimes.MoonFraction = ((1 + cos(inc)) / 2).toFloat()
    theStarTimes.MoonAngle = angle.toFloat()
    if (angle < 0)
        theStarTimes.MoonPhase = (0.5 + 0.5 * inc * -1  / PI).toFloat()
    else
        theStarTimes.MoonPhase = (0.5 + 0.5 * inc  / PI).toFloat()
}






// Subs ============================================================================================

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

private fun rightAscension(l: Double, b: Double): Double {
    return atan2(sin(l) * cos(E_O) - tan(b) * sin(E_O), cos(l))
}

private fun hoursLater(date: Double, h: Double): Double {
    return date + h * 5 / 120
}

private fun GetMoonAltitude(date: Double, h: Double): Double {
    return date + h * 5 / 120
}

private fun moonCoords(j2k:Double): TObjCoords {
    val L: Double
    val M: Double
    val F: Double
    val lg: Double
    val b: Double
    val dt: Double
    L = D2R * (218.316 + 13.176396 * j2k) // ecliptic longitude
    M = D2R * (134.963 + 13.064993 * j2k) // mean anomaly
    F = D2R * (93.272 + 13.229350 * j2k) // mean distance
    lg = L + D2R * 6.289 * sin(M) // longitude
    b = D2R * 5.128 * sin(F) // latitude
    dt = 385001 - 20905 * cos(M) // distance to the moon in km
    val result = TObjCoords()
    result.ra = rightAscension(lg,b)
    result.dec = declination(lg,b);
    result.dist = dt
    return result
}

private fun  siderealTime(d: Double, lw: Double): Double {
    return D2R * (280.16 + 360.9856235 * d) - lw
}

private fun  GetAltitude(H: Double, phi: Double, dec:Double): Double {
    return asin(sin(phi) * sin(dec) + cos(phi) * cos(dec) * cos(H))
}

private fun  GetMoonAltitude(d: TJD2K, lat: TCoords, lng: TCoords): Double{
    val lw: Double
    val phi: Double
    val H: Double
    var hg: Double

    lw  = D2R * -lng;
    phi = D2R * lat;
    val mc = moonCoords(d)
    H = siderealTime(d, lw) - mc.ra
    hg = GetAltitude(H, phi, mc.dec)
    // altitude correction for refraction
    hg = hg + D2R * 0.017 / tan(hg + D2R * 10.26 / (hg + D2R * 5.10));
    return hg;
}

private fun sunCoords(myDate: TJD2K): TObjCoords {
    val M: Double
    val L: Double
    val result = TObjCoords()
    M = solarMeanAnomaly(myDate);
    L = eclipticLongitude(M);
    result.dec = declination(L, 0.0)
    result.ra = rightAscension(L, 0.0)
    return result
}

fun ln_get_julian_day(date: ln_date): TJD {
    val extra = 100.0 * date.years + date.months - 190002.5
    return 367.0 * date.years -
            Math.floor(7.0 * (date.years + Math.floor((date.months + 9.0) / 12.0)) / 4.0) +
            Math.floor(275.0 * date.months / 9.0) +
            date.days + (date.hours + (date.minutes + date.seconds / 60.0) / 60.0) / 24.0 +
            1721013.5 - 0.5 * extra / Math.abs(extra) + 0.5
}

//void GetMoonPosition (const ln_date *date, TCoords lat, TCoords lng, TMoonPosition * Result){
//double lw, phi, d, H, hg;
//TObjCoords mc;
//
//  lw  := D2R * -lng;
//  phi := D2R * lat;
//  d  := ToDays(date);
//
//  moonCoords(d, &mc);
//  H  := siderealTime(d, lw) - mc.ra;
//  hg := altitude(H, phi, mc.dec);
//  // altitude correction for refraction
//  hg := hg + D2R * 0.017 / tan(hg + D2R * 10.26 / (hg + D2R * 5.10));
//
//  Result->azimuth  := azimuth(H, phi, mc.dec);
//  Result->altitude := hg;
//  Result->distance := mc.dist;
//}
