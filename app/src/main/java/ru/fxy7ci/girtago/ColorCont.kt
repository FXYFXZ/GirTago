package ru.fxy7ci.girtago

import android.graphics.Color
import androidx.annotation.ColorInt

class ColorCont (@ColorInt startColor: Int) {
    private var theColor: Int = Color.RED
    init {
        theColor = startColor
    }
    fun getColor(): Int{
        return theColor
    }

    fun acceptMove(mx: Float){
        //TODO работаем с компонентом
        theColor += mx.toInt()
    }


}