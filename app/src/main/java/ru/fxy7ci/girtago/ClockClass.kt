package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.res.ResourcesCompat
import ru.fxy7ci.girtago.astroLib.AstroControls.TJD
import ru.fxy7ci.girtago.astroLib.theStarTimes
import kotlin.math.floor

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

    override fun onDraw(canvas: Canvas) {
        mainRadius = width/2.toFloat()
        canvas.translate(mainRadius, mainRadius)
        theCanvas = canvas
        if (!theStarTimes.isSet) return // данные ещё не готовы


        drawSun(getAngFromJul(theStarTimes.sunRise), getAngFromJul(theStarTimes.sunSet))
        drawMoon(getAngFromJul(theStarTimes.moonRise), getAngFromJul(theStarTimes.moonSet))
        drawHand()

    }

    // inners---------------------------------------------------------------------------------------
    //
    private fun getAngFromJul(myJD : TJD): Float {
        var res = myJD - theStarTimes.noon
        res -= floor(res)
        if (res<0) res += 1
        if (res>1) res -= 1
        res *= 360f
        res -= 90
        res %= 360
        return res.toFloat()
    }

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

    private fun drawHand(){
        // Ось
        paint.style = Paint.Style.FILL
        paint.color = ResourcesCompat.getColor(resources, R.color.ClockHand,null)
        val hWidth = sunWidth*1.5.toFloat()
        theCanvas.drawCircle(centerX,centerY, hWidth, paint)

        // стрелка >-
        theCanvas.save()
//        theCanvas.rotate(getAngFromJul(theStarTimes.noon))
        theCanvas.rotate(getAngFromJul(theStarTimes.JD-0.125))
        val pt = Path()
        pt.reset()
        pt.moveTo(0f,hWidth*0.7f)
        pt.lineTo((mainRadius-sunWidth), 0f)
        pt.lineTo(0f, -hWidth*0.7f)
        pt.close()
        theCanvas.drawPath(pt,paint)
        theCanvas.restore()

    }



    // Export
    fun gotoDayB4(myCnt: Int) {
        




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