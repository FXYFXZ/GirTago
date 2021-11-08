package ru.fxy7ci.girtago

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.core.view.GestureDetectorCompat

lateinit var btnSlide: Button

class MainActivity : AppCompatActivity() {
    private lateinit var mDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setGest()
    }

    private fun setGest() {
        btnSlide = findViewById(R.id.btnSlide)
        mDetector = GestureDetector(MyGestureListener())
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
        Log.d("MyLog", "onScroll: ")
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
