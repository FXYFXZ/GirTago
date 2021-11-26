package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.*
import android.graphics.drawable.shapes.ArcShape
import android.view.View
import androidx.core.content.res.ResourcesCompat

class MyClock(context: Context?) : View(context) {
    // Внутренние переменные
    var paint: Paint = Paint()
    var centerX = 0f
    var centerY = 0f
    var radius = 20f

    override fun onDraw(canvas: Canvas) {
        centerX = (width/2).toFloat()
        centerY = centerX
        radius  = centerX*0.9f

        // Suno
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockSun,null)
        val rect = RectF(20f, 20f, width.toFloat(), width.toFloat())
        canvas.drawArc(rect, 15f,125f,  true, paint)

        // циферблат
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockBack,null)
        canvas.drawCircle(centerX,centerY, radius,paint)




//        paint.color = Color.GREEN
//        val rect = Rect(100, 255, 200, 300)
//        canvas.drawRect(rect, paint)
    }

    fun setMyDate(myRad: Float){
        radius += myRad
        invalidate()
    }


}