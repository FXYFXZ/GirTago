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

    val sunWidth = 30f
    val lunWidth = 30f

    override fun onDraw(canvas: Canvas) {
        centerX = (width/2).toFloat()
        centerY = centerX
        radius  = centerX*0.9f

        var theRect = RectF(0f, 0f, width.toFloat(), width.toFloat())

        // Suno ------------------------------------------------------------------------
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockSun,null)
        paint.strokeWidth = 30f
        paint.style = Paint.Style.STROKE

        val sAup   = 180f+30f
        val sAdown = 180f-2*30

        val sunRect = scaleRectByVal(theRect, -sunWidth)
        canvas.drawArc(sunRect, sAup,sAdown,  false, paint)
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockNight,null)
        canvas.drawArc(sunRect, sAup,-sAdown,  false, paint)

        // циферблат
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockBack,null)
        canvas.drawCircle(centerX,centerY, radius,paint)

        // стрелка
        paint.style = Paint.Style.FILL
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockHand,null)
        canvas.drawCircle(centerX,centerY, sunWidth, paint)




//        paint.color = Color.GREEN
//        val rect = Rect(100, 255, 200, 300)
//        canvas.drawRect(rect, paint)
    }

    fun setMyDate(myRad: Float){
        radius += myRad
        invalidate()
    }


}

// Lib
fun scaleRectByVal(myRect: RectF, myVal: Float): RectF {
    myRect.left -= myVal
    myRect.top  -= myVal
    myRect.bottom += myVal
    myRect.right += myVal
    return myRect
}