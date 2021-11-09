package ru.fxy7ci.girtago

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

lateinit var clrCnt: ColorCont
lateinit var btnSlide: Button

class MainActivity : AppCompatActivity() {
    private lateinit var mDetector: GestureDetector
    lateinit var txClass: TxThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // инициализация классов
        txClass = TxThread()

        showTxState()
        btnSlide = findViewById(R.id.btnSlide)
        clrCnt   = ColorCont()
        btnSlide.setBackgroundColor(clrCnt.getColor())
        setGest()

//        val spinner :Spinner = findViewById(R.id.cbbTxState)
//        ArrayAdapter.createFromResource( )


    }

    private fun showTxState(){
        when (txClass.getState()) {
            TxThread.State.INIT ->
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
        Log.d("MyLog", "onDown: ")
        // don't return false here or else none of the other
        // gestures will work
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d("MyLog", "onSingleTapConfirmed: ")
        btnSlide.setBackgroundColor(Color.BLUE)
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.d("MyLog", "onLongPress: ")
        btnSlide.setBackgroundColor(Color.RED)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d("MyLog", "onDoubleTap: ")
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
        Log.d("MyLog", "onFling: ")
        return true
    }
}
