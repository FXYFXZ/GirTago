package ru.fxy7ci.girtago

import android.R.attr
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.R.attr.button
import android.content.res.Resources

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.ColorInt


lateinit var clrCnt: ColorCont
lateinit var btnSlide: Button

class MainActivity : AppCompatActivity() {
    private lateinit var mDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSlide = findViewById(R.id.btnSlide)



        setGest()


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
        btnSlide.setBackgroundColor(Color.GREEN)
        return true
    }

    override fun onScroll(
        e1: MotionEvent, e2: MotionEvent,
        distanceX: Float, distanceY: Float
    ): Boolean {
        Log.d("MyLog", "onScroll: " + distanceX.toString())
        //clrCnt.acceptMove(distanceX)
       // btnSlide.setBackgroundColor(clrCnt.getColor())
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
