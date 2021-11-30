package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fxy7ci.girtago.astroLib.AstroControls.ln_date
import ru.fxy7ci.girtago.astroLib.Astroclass.*
import ru.fxy7ci.girtago.databinding.FragmentClockBinding
import java.text.SimpleDateFormat
import java.util.*

class ClockFragment : Fragment(R.layout.fragment_clock) {
    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!
    private lateinit var myClock : MyClock
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClockBinding.inflate(inflater,container, false)

        binding.btnNext.setOnClickListener {
            myClock.setMyDate(2f)
        }
        iniCals() // Инициализация расчета
        return binding.root
    }

    override fun onResume() {
        printDates()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myClock = MyClock(activity)
        binding.clockContainer.addView(myClock)
    }

    private fun iniCals(){
        val currentDate = Date()
        val theDate = ln_date()
        theDate.days = currentDate.day.toByte()
        theDate.months = currentDate.month.toByte()
        theDate.years = currentDate.year.toShort()
        theDate.hours = currentDate.hours.toByte()
        theDate.minutes = currentDate.minutes.toByte()
        theDate.seconds = currentDate.seconds.toByte()
        astroSetSunTimes(theDate, StoreVals.LATITUDE, StoreVals.LONGITUDE)

    }

    // Отображаем актуальное текущее время
    private fun printDates() {
        val currentDate = Date()
// Форматирование времени как "день.месяц.год"
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)

// Форматирование времени как "часы:минуты:секунды"
//        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//        val timeText: String = timeFormat.format(currentDate)

        binding.tvDateTime.text = dateText
        binding.tvJDay.text = "-"
        binding.tvSunRise.text = theStarTimes.sunRise.toString()
        binding.SunNoon.text = theStarTimes.Noon.toString()
        binding.SunSet.text = theStarTimes.sunSet.toString()
    }



} // CLASS =========================================================================================
