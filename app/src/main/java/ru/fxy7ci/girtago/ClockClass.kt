package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.res.ResourcesCompat

class MyClock(context: Context?) : View(context) {
    // Внутренние переменные
    private lateinit var theCanvas : Canvas
    private var paint: Paint = Paint()
    private var mainRadius = 0f

    // пока константы
    private val sunWidth = 30f
    private val centerX = 0f
    private val centerY = 0f

    //шняга
    private var myAngel = 10f

    override fun onDraw(canvas: Canvas) {
        mainRadius = width/2.toFloat()
        canvas.translate(mainRadius, mainRadius)
        theCanvas = canvas
        drawSun((180+20).toFloat(), (360-20).toFloat())
        drawMoon(90f, 290f)
        drawHand(myAngel)

    }

    // inners---------------------------------------------------------------------------------------

    // Солнце по старту и концу уголы
    private fun drawSun(myStart: Float, myEnd: Float){
        // Suno ------------------------------------------------------------------------
        // циферблат
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = sunWidth*0.7f
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockNight,null)
        theCanvas.drawCircle(centerX,centerY, mainRadius-sunWidth/2,paint)

        paint.style = Paint.Style.STROKE
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockSun,null)
        paint.strokeWidth = sunWidth
        paint.style = Paint.Style.STROKE
        val theRect = RectF(-(mainRadius-sunWidth/2), -(mainRadius-sunWidth/2),
            mainRadius-sunWidth/2, mainRadius-sunWidth/2)
         drawMyArc(myStart, myEnd,  theRect)
    }

    // Рисуем дугу текущим цветом по началу и концу
    private fun drawMyArc(myStart: Float, myEnd: Float, myRect: RectF){
        val cAng = 360f
        val angDlit: Float
        if (myEnd > myStart) {
            angDlit = myEnd - myStart
        }
        else {
            angDlit = cAng-myStart + myEnd
        }
        theCanvas.drawArc(myRect, myStart ,angDlit,  false, paint)
    }

    private fun drawMoon(myStart: Float, myEnd: Float){
        paint.style = Paint.Style.STROKE
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockMoon,null)
        paint.strokeWidth = sunWidth*0.9f
        val moonRad :Float = mainRadius*0.8f
//z       val theRect = RectF(-1*moonRad, -1*moonRad, 0f, 0f)
        val mnRect = RectF(-moonRad,-moonRad,moonRad,moonRad)
        drawMyArc(myStart, myEnd, mnRect)
    }

    private fun drawHand(myAng: Float){
        // Ось
        paint.style = Paint.Style.FILL
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockHand,null)
        val hWidth = sunWidth*1.5.toFloat()
        theCanvas.drawCircle(centerX,centerY, hWidth, paint)

        // стрелка
        theCanvas.save()
        theCanvas.rotate(-myAng)
        val pt = Path()
        pt.reset()
        pt.moveTo(-hWidth*0.7f, 0f)
        pt.lineTo(0f, -(mainRadius-sunWidth))
        pt.lineTo(hWidth*0.7f, 0f)
        pt.close()
        theCanvas.drawPath(pt,paint)
        theCanvas.restore()

    }



    // Export
    fun setMyDate(myRad: Float){
        myAngel += 10f
        invalidate()
    }


} // CLASS------------------------------------------------------------------------------------------


// to Lib ------------------------------------------------------------------------------------------
fun scaleRectByVal(myRect: RectF, myVal: Float): RectF {
    myRect.left -= myVal
    myRect.top  -= myVal
    myRect.bottom += myVal
    myRect.right += myVal
    return myRect
}