package ru.fxy7ci.girtago

import android.graphics.Color
import java.lang.Math.abs

/** Контейнер для работы с лампами
 *
 */

class ColorCont() {
    private var hsv = FloatArray(3)
    private val  SATURATION = 1f // по умолчанию насыщенность

    init {
        setDefault()
    }
    // Ставим значение по умолчанию
    fun setDefault(){
        Color.colorToHSV(Color.rgb(0,80,80), hsv)
    }

    fun getColor(): Int{
        return Color.HSVToColor(hsv)
    }

    // Двигаем цвет
    fun moveHue(myVal: Float){
        hsv[1] = SATURATION
        hsv[0] += myVal
        if (hsv[0] < 0) hsv[0] = 0f
        if (hsv[0] > 360) hsv[0] = 360f
    }
    fun moveValue(myVal: Float){
        if (kotlin.math.abs(myVal) > 1f) return
        hsv[1] = SATURATION
        hsv[2] += myVal
        if (hsv[2] < 0) hsv[2] = 0f
        if (hsv[2] > 1) hsv[2] = 1f
    }


}