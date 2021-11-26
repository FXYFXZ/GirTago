package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class MyClock(context: Context?) : View(context) {
    // Внутренние переменные

    var paint: Paint = Paint()
    var centerX = 255f
    var centerY = 255f
    var radius = 20f

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.GREEN
        val rect = Rect(100, 255, 200, 300)
        canvas.drawRect(rect, paint)
        paint.color = Color.BLUE
        canvas.drawCircle(centerX,centerY, radius,paint)
    }

    fun setMyDate(myRad: Float){
        radius += myRad
        invalidate()
    }


}