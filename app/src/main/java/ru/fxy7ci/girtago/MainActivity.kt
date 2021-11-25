package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import ru.fxy7ci.girtago.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Интерфейс
        val firstFragment = ClockFragment()
        val settingsFragment = SettingsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit()
        }

        binding.btnFragment1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, firstFragment)
                commit()
            }
        }

        binding.btnFragment2.setOnClickListener {
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








