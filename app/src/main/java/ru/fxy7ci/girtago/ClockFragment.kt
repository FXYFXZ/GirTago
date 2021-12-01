package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fxy7ci.girtago.astroLib.*
import ru.fxy7ci.girtago.astroLib.AstroControls.TJD
import ru.fxy7ci.girtago.astroLib.AstroControls.ln_date
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
        val theDate = ln_date()
        val rightNow = Calendar.getInstance()
        theDate.days  = rightNow.get(Calendar.DATE).toByte()
        theDate.months = (rightNow.get(Calendar.MONTH)+1).toByte()
        theDate.years = rightNow.get(Calendar.YEAR).toShort()
        var hours = rightNow.get(Calendar.HOUR)
        if (rightNow.get(Calendar.AM_PM)==Calendar.PM) hours += 12
        theDate.hours = hours.toByte()
        theDate.minutes = rightNow.get(Calendar.MINUTE).toByte()
        theDate.seconds = rightNow.get(Calendar.SECOND).toByte()
        astroSetSunTimes(theDate, StoreVals.LATITUDE, StoreVals.LONGITUDE)
        astroSetMoonTimes(theDate, StoreVals.LATITUDE, StoreVals.LONGITUDE)
        //astroSetMoonIllumination(theDate)
        theStarTimes.isSet = true
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
        val sfmt = "S: ${printTimeFromJD(theStarTimes.sunRise)} - ${printTimeFromJD(theStarTimes.sunSet)}"
        binding.tv1.text = sfmt
        val lfmt = "L: ${printTimeFromJD(theStarTimes.moonRise)} - ${printTimeFromJD(theStarTimes.moonSet)} "
        binding.tv2.text = lfmt

    }


private fun printTimeFromJD(myTime:TJD): String{
    val lnDate = getDateFromJD(myTime + StoreVals.LOCAL_JMT)
    return lnDate.hours.toString() + ":" + lnDate.minutes.toString()
}




} // CLASS =========================================================================================
