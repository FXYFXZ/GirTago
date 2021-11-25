package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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


}
// END CLASS








