package ru.fxy7ci.girtago

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Интерфейс
        val firstFragment = ClockFragment()
        val settingsFragment = SettingsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit()
        }

        val btnCl: Button = findViewById(R.id.btnFragment1)
        val btnC2: Button = findViewById(R.id.btnFragment2)

        btnCl.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, firstFragment)
                commit()
            }
        }

        btnC2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, settingsFragment)
                addToBackStack(null)
                commit()
            }
        }




    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        savePreferences()
        super.onPause()
//        mainHandler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
//        menu?.findItem(R.id.menu_btOff)?.isVisible = (myAppState ==AppState.AP_BT_PROBLEM)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.menu_connect -> mBluetoothLeService?.connect(StoreVals.DeviceAddress)
//            R.id.menu_disconnect -> {
//                invalidateOptionsMenu()
//            }
//        }
        return super.onOptionsItemSelected(item)
    }

    //------------------------------------------------------ своё
    //==============================================================================

    private fun savePreferences(){
//        val sharedPreferences = getSharedPreferences(StoreVals.APP_PREFERENCES , MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putInt("RED", color.red)
//        editor.putInt("GREEN",color.green)
//        editor.putInt("BLUE",color.blue)
//        editor.apply()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setGest() {
//        mDetector =GestureDetector(this, MyGestureListener())
//        //GestureDetector (MyGestureListener())
//        btnSlide.setOnTouchListener { _, event ->
//            mDetector.onTouchEvent(event)
//        }
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean {
            //TODO запускаем отсчет
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//            btnSlide.setBackgroundColor(Color.BLUE)
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            //TODO меню на компоненте
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
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

}
// END CLASS








