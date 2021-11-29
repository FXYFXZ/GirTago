package ru.fxy7ci.girtago

import ru.fxy7ci.girtago.AstroLib.*
import java.lang.Math.PI

// Расчет событий восхода и захода солнца и луны
typealias TJD2K = Double

private val J2000 =  2451545  // Юлианский день 2000 г.
private val D2R = (1.7453292519943295769e-2)  /* deg->radian */
private val DM_PI = (2*PI)
private val RADIAN = (180.0 / PI)

private val  J0 = 0.0009
private val E_O = (D2R * 23.4397)  // Наклон земной оси



