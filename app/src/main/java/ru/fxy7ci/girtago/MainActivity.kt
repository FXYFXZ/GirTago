package ru.fxy7ci.girtago

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

lateinit var clrCnt: ColorCont
lateinit var btnSlide: Button

class MainActivity : AppCompatActivity() {
    private lateinit var mDetector: GestureDetector
    private lateinit var txClass: TxThread
    private val mainHandler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // инициализация классов
        txClass = TxThread()
      //  txClass.isDaemon = true

        showTxState()
        btnSlide = findViewById(R.id.btnSlide)
        clrCnt   = ColorCont()
        btnSlide.setBackgroundColor(clrCnt.getColor())

        setGest()

        mainHandler.post(object : Runnable {
            override fun run() {
                showTxState()
                mainHandler.postDelayed(this, 1000)
            }
        })

    }


    override fun onPause() {
        mainHandler.removeCallbacksAndMessages(null)
        txClass.getAppStopped()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (!txClass.toStop) txClass.start()
    }

    private fun showTxState(){
        val fldDtate : TextView = findViewById(R.id.fldState)
        when (txClass.theTXState()) {
            TxThread.State.INIT -> fldDtate.text = "Инициализация"
            TxThread.State.ERROR -> fldDtate.text = "Ошибка"
            TxThread.State.READY -> fldDtate.text = "Работа"
            else -> fldDtate.text = "???"
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setGest() {
        mDetector = GestureDetector (MyGestureListener())
        btnSlide.setOnTouchListener { v, event ->
            mDetector.onTouchEvent(event)
        }
    }

}

class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
    override fun onDown(event: MotionEvent): Boolean {
        //TODO запускаем отсчет
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        //TODO либо вставка либо toggle
        btnSlide.setBackgroundColor(Color.BLUE)
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        //TODO меню на компоненте
        btnSlide.setBackgroundColor(Color.RED)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        clrCnt.setDefault()
        btnSlide.setBackgroundColor(clrCnt.getColor())
        return true
    }

    override fun onScroll(
        e1: MotionEvent, e2: MotionEvent,
        distanceX: Float, distanceY: Float
    ): Boolean {
        clrCnt.moveHue(distanceX * (ColorCont.MAX_HUE/2)  / btnSlide.width )
        clrCnt.moveValue(distanceY / btnSlide.width )
        btnSlide.setBackgroundColor(clrCnt.getColor())
        return true
    }

    override fun onFling(
        event1: MotionEvent, event2: MotionEvent,
        velocityX: Float, velocityY: Float
    ): Boolean {
//        Log.d("MyLog", "onFling: ")
        return true
    }
}
