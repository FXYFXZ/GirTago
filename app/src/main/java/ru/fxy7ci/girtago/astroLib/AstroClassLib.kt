package ru.fxy7ci.girtago.astroLib
import ru.fxy7ci.girtago.astroLib.AstroControls.*
import java.util.*
import kotlin.math.*


// Расчет событий восхода и захода солнца и луны

private const val D2R = (1.7453292519943295769e-2)  /* deg->radian */

// шняга, вроде не нужно
// private val DM_PI = (2*PI)
// private val RADIAN = (180.0 / PI)

private const val J0 = 0.0009
private const val E_O = (D2R * 23.4397)  // Наклон земной оси
private val SUN_CONST = arrayOf(-0.833, -0.3, -0.6, -12, -18, 6)

// переменная главного расчета
var theStarTimes = StarTimes()

fun astroSetSunTimes(date: ln_date, lat: TCoords, lng: TCoords) {
    val dec: Double; val n: Short
    val lw = D2R * -lng
    val phi = D2R * lat

    val d  = toDays(date)
    n  = julianCycle(d, lw) // здесь теряем своё время, оставляем только дату
    val ds = approxTransit(0.0, lw, n)
    val m  = solarMeanAnomaly(ds)
    val l  = eclipticLongitude(m)
    dec = declination(l, 0.0)

    theStarTimes.Noon = solarTransitJ(ds, m, l) // Полдень на юлианский день
    val h : Double = SUN_CONST[TSunTimes.SST_SET.ordinal] as Double
    theStarTimes.sunSet  = getSetJ(h * D2R, lw, phi, dec, m, l, n)
    theStarTimes.sunRise = theStarTimes.Noon - (theStarTimes.sunSet - theStarTimes.Noon)
}

fun astroSetMoonTimes(date: ln_date, lat: TCoords, lng: TCoords){
    var h0: Double; var h1: Double; var h2: Double
    var a: Double;  var b: Double;  var xe: Double; var ye: Double
    var d: Double;  var x1 = 0.0; var x2 = 0.0; var dx: Double
    var mrise =0.0;  var mset =0.0
    var roots: Int
    val tMdn: TJD2K // Дата полуночи

    val ldate = date
    ldate.hours = 0
    ldate.minutes = 0
    ldate.seconds = 0
    tMdn = toDays(ldate)

    val hc = 0.133 * D2R
    h0 = getMoonAltitude(tMdn, lat, lng) - hc
    // go in 2-hour chunks, each time seeing if a 3-point quadratic curve crosses zero
    // (which means rise or set)
    theStarTimes.moonRise = 0.0
    theStarTimes.moonSet = 0.0

    var i = 1
    var risefound = false
    var setfound  = false
    do {
        h1 = getMoonAltitude(hoursLater(tMdn, i.toDouble()), lat, lng) - hc
        h2 = getMoonAltitude(hoursLater(tMdn, (i+1).toDouble()), lat, lng) - hc
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
            if (abs(x1) <= 1) roots += 1
            if (abs(x2) <= 1) roots += 1
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
        theStarTimes.moonRise = hoursLater(tMdn, mrise)
    else
        theStarTimes.moonRise = 0.0

    if (setfound)
        theStarTimes.moonSet  = hoursLater(tMdn, mset)
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

fun astroSetMoonIllumination(myDate: ln_date){
    val phi: Double
    val inc: Double
    val angle: Double
    val sdist = 149598000 // distance from Earth to Sun in km
    val tmpDate = toDays(myDate)
    val sc = sunCoords(tmpDate)
    val mc = moonCoords(tmpDate)
    phi = acos(sin(sc.dec) * sin(mc.dec) + cos(sc.dec) * cos(mc.dec) * cos(sc.ra - mc.ra))
    inc = atan2(sdist * sin(phi), mc.dist - sdist * cos(phi))
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

private fun toDays(myDate: ln_date): TJD2K {
    return (getJulianDay(myDate) - ECHO_JMT - J2000 )
}

private fun julianCycle(d: Double, lw:Double): Short {
    return round(d - J0 - lw/(2*PI)).toInt().toShort()
}

private fun approxTransit(Ht:Double, lw:Double, n: Short): Double {
    return J0 + (Ht+lw)/(2*PI) + n
}

private fun solarMeanAnomaly(j2k:Double): Double {
    return D2R * (357.5291 + 0.98560028 * j2k)
}
private fun eclipticLongitude(M:Double): Double {
    val c: Double =
        D2R * (1.9148 * sin(M) + 0.02 * sin(2 * M) + 0.0003 * sin(3 * M)) // equation of center
    val p = D2R * 102.9372 // perihelion of the Earth
    return M + c + p + PI
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
    val w = hourAngle(h, phi, dec)
    val a = approxTransit(w, lw, n)
    return solarTransitJ(a, M, L)
}

private fun rightAscension(l: Double, b: Double): Double {
    return atan2(sin(l) * cos(E_O) - tan(b) * sin(E_O), cos(l))
}

private fun hoursLater(date: Double, h: Double): Double {
    return date + h * 5 / 120
}

private fun getMoonAltitude(date: Double, h: Double): Double {
    return date + h * 5 / 120
}

private fun moonCoords(j2k:Double): TObjCoords {
    val lg: Double; val b: Double; val dt: Double
    val l = D2R * (218.316 + 13.176396 * j2k) // ecliptic longitude
    val m = D2R * (134.963 + 13.064993 * j2k) // mean anomaly
    val f = D2R * (93.272 + 13.229350 * j2k) // mean distance
    lg = l + D2R * 6.289 * sin(m) // longitude
    b = D2R * 5.128 * sin(f) // latitude
    dt = 385001 - 20905 * cos(m) // distance to the moon in km
    val result = TObjCoords()
    result.ra = rightAscension(lg,b)
    result.dec = declination(lg,b)
    result.dist = dt
    return result
}

private fun  siderealTime(d: Double, lw: Double): Double {
    return D2R * (280.16 + 360.9856235 * d) - lw
}

private fun  getAltitude(H: Double, phi: Double, dec:Double): Double {
    return asin(sin(phi) * sin(dec) + cos(phi) * cos(dec) * cos(H))
}

private fun  getMoonAltitude(d: TJD2K, lat: TCoords, lng: TCoords): Double{
    val lw  = D2R * -lng
    val phi = D2R * lat
    val mc = moonCoords(d)
    val h = siderealTime(d, lw) - mc.ra
    var hg = getAltitude(h, phi, mc.dec)
    // altitude correction for refraction
    hg += D2R * 0.017 / tan(hg + D2R * 10.26 / (hg + D2R * 5.10))
    return hg
}

private fun sunCoords(myDate: TJD2K): TObjCoords {
    val result = TObjCoords()
    val m = solarMeanAnomaly(myDate)
    val l = eclipticLongitude(m)
    result.dec = declination(l, 0.0)
    result.ra = rightAscension(l, 0.0)
    return result
}

fun getJulianDay(date: ln_date): TJD {
    val extra = 100.0 * date.years + date.months - 190002.5
    return 367.0 * date.years -
            floor(7.0 * (date.years + floor((date.months + 9.0) / 12.0)) / 4.0) +
            floor(275.0 * date.months / 9.0) +
            date.days + (date.hours + (date.minutes + date.seconds / 60.0) / 60.0) / 24.0 +
            1721013.5 - 0.5 * extra / abs(extra) + 0.5
}

fun getDateFromJD(mjd: TJD): ln_date {
    val result = ln_date()
    var l = mjd.toInt() + 68569
    val n = 4 * l / 146097
    l -= (146097 * n + 3) / 4
    val i = 4000 * (l + 1) / 1461001
    l = l - 1461 * i / 4 + 31
    val j = 80 * l / 2447
    val d = l - 2447 * j / 80
    l = j / 11
    val m = j + 2 - 12 * l
    val y = 100 * (n - 49) + i + l

    val fraction: Double = mjd - floor(mjd)
    val dHours = fraction * 24.0
    val hours = dHours.toInt()
    val dMinutes = (dHours - hours) * 60.0
    val minutes = dMinutes.toInt()
    val seconds: Int = ((dMinutes - minutes) * 60.0).toInt()

    result.years = y.toShort()
    result.months = m.toByte()
    result.days = d.toByte()
    result.hours =  ((hours +12)%24).toByte()
    result.minutes = minutes.toByte()
    result.seconds = seconds.toByte()

    return result
}

//val z: Int
//val jd: Double = mjd + 2400000.5 + 0.5 // if a JDN is passed as argument,
//// omit the 2400000.5 term
//
//var x: Double
//
//z = floor(jd).toInt()
//var f: Double = jd - z
//val a :Int = if (z >= 2299161) {
//    val alpha = floor((z - 1867216.25) / 36524.25).toInt()
//    z + 1 + alpha - floor((alpha / 4).toDouble()).toInt()
//} else z
//val b: Int = a + 1524
//val c: Int = floor((b   - 122.1) / 365.25).toInt()
//val d: Int = floor(365.25 * c).toInt()
//val e: Int = floor((b - d) / 30.6001).toInt()
//result.days = (b - d - floor(30.6001 * e).toInt()).toByte()
//result.months = if (e<14) (e-1).toByte() else (e-13).toByte()
//result.years = if(result.months>2) (c-4716).toShort()  else (c-4715).toShort()
//
//f *= 24.0
//x = floor(f)
//result.hours = x.toInt().toByte()
//f -= x
//
//f *= 60.0
//x = floor(f)
//result.minutes = x.toInt().toByte()
//f -= x
//
//f *= 60.0
//x = floor(f)
//result.seconds = x.toInt().toByte()


// void GetMoonPosition (const ln_date *date, TCoords lat, TCoords lng, TMoonPosition * Result){
// double lw, phi, d, H, hg;
// TObjCoords mc;
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
