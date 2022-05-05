package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import ru.fxy7ci.girtago.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Интерфейс
        settingsFragment = SettingsFragment()
        val firstFragment = ClockFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit() // switch
        }

        // что насчет сетпа

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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_settings -> gotoSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotoSettings(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, settingsFragment)
            addToBackStack(null)
            commit()
        }
    }

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








