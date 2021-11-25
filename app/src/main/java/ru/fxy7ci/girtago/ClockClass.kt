package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

private class MyRectangle(context: Context?) : View(context) {
    var paint = Paint()
    override fun onDraw(canvas: Canvas) {
        paint.color = Color.GREEN
        val rect = Rect(20, 56, 200, 112)
        canvas.drawRect(rect, paint)
    }
}