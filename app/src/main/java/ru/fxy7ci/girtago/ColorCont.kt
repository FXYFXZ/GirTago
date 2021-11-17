package ru.fxy7ci.girtago
import android.graphics.Color
import android.widget.Button
import androidx.core.graphics.red

/** Контейнер для работы с лампами
 * =============================== */

class ColorCont(myRed: Int, myGreen: Int, myBlue: Int) {

    private var hsv = FloatArray(3)
    init {
        setDefault()
    }

    init {
        Color.colorToHSV(Color.rgb(myRed,myGreen,myBlue), hsv)
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
        if (hsv[0] > 360) hsv[0] = MAX_HUE
    }

    fun moveValue(myVal: Float){
        if (kotlin.math.abs(myVal) > 1f) return
        hsv[1] = SATURATION
        hsv[2] += myVal
        if (hsv[2] < 0) hsv[2] = 0f
        if (hsv[2] > 1) hsv[2] = MAX_VAL
    }


    companion object {
        const val MAX_HUE = 360f
        const val MAX_VAL = 1f
        const val  SATURATION = this.MAX_VAL
    }


}
