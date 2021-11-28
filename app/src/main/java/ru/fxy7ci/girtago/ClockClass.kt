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
        drawSun()
        drawHand(myAngel)

//        paint.color = Color.GREEN
//        val rect = Rect(100, 255, 200, 300)
//        canvas.drawRect(rect, paint)
    }

    // inners---------------------------------------------------------------------------------------




    private fun drawSun(){
        // Suno ------------------------------------------------------------------------
        // циферблат
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = sunWidth
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockNight,null)
        theCanvas.drawCircle(centerX,centerY, mainRadius-sunWidth/2,paint)


        paint.color = ResourcesCompat.getColor(resources, R.color.ClockSun,null)
        paint.strokeWidth = sunWidth
        paint.style = Paint.Style.STROKE
        val theRect = RectF(-(mainRadius-sunWidth/2), -(mainRadius-sunWidth/2),
            mainRadius-sunWidth/2, mainRadius-sunWidth/2)
        val sAup   = 180f+30f
        val sAdown = 180f-2*30

//        val sunRect = scaleRectByVal(theRect, -sunWidth)
        theCanvas.drawArc(theRect, sAup,sAdown,  false, paint)

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


    private fun drawMoon(){

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