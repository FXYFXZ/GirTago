package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fxy7ci.girtago.astroLib.AstroControls.TJD
import ru.fxy7ci.girtago.astroLib.AstroControls.ln_date
import ru.fxy7ci.girtago.astroLib.astroSetMoonTimes
import ru.fxy7ci.girtago.astroLib.astroSetSunTimes
import ru.fxy7ci.girtago.astroLib.getDateFromJD
import ru.fxy7ci.girtago.astroLib.theStarTimes
import ru.fxy7ci.girtago.databinding.FragmentClockBinding
import java.text.SimpleDateFormat
import java.util.*

class ClockFragment : Fragment(R.layout.fragment_clock) {
    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!
    private lateinit var myClock : MyClock
    private val theDateCalendar = Calendar.getInstance() // currenct DateTime
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClockBinding.inflate(inflater,container, false)

        binding.btnDayBefore.setOnClickListener{
            theDateCalendar.add(Calendar.DATE,-1)
            reCalcuate(); printDates()
            myClock.invalidate() // Update
        }



        return binding.root
    }

    override fun onResume() {
        reCalcuate() // Инициализация расчета
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

    // Перерасчет всех необходимых величин для индикации
    private fun reCalcuate(){
        val theDate = ln_date()
        theDate.days  = theDateCalendar.get(Calendar.DATE).toByte()
        theDate.months = (theDateCalendar.get(Calendar.MONTH)+1).toByte()
        theDate.years = theDateCalendar.get(Calendar.YEAR).toShort()
        var hours = theDateCalendar.get(Calendar.HOUR)
        if (theDateCalendar.get(Calendar.AM_PM)==Calendar.PM) hours += 12
        theDate.hours = hours.toByte()
        theDate.minutes = theDateCalendar.get(Calendar.MINUTE).toByte()
        theDate.seconds = theDateCalendar.get(Calendar.SECOND).toByte()
        astroSetSunTimes(theDate, StoreVals.LATITUDE, StoreVals.LONGITUDE)
        astroSetMoonTimes(theDate, StoreVals.LATITUDE, StoreVals.LONGITUDE)
        //astroSetMoonIllumination(theDate)
        theStarTimes.isSet = true // расчет готов
    }

    // Отображаем актуальное текущее время
    private fun printDates() {
        val currentDate =  Date()
// Форматирование времени как "день.месяц.год"
//        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
//        val dateText: String = dateFormat.format(currentDate)



        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val dateText: String = format.format(theDateCalendar.time)


// Форматирование времени как "часы:минуты:секунды"
//        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//        val timeText: String = timeFormat.format(currentDate)

        binding.tvDateTime.text = dateText
        val sfmt = "S: ${printTimeFromJD(theStarTimes.sunRise)} - ${printTimeFromJD(theStarTimes.sunSet)}"
       // binding.tv1.text = sfmt
        val lfmt = "L: ${printTimeFromJD(theStarTimes.moonRise)} - ${printTimeFromJD(theStarTimes.moonSet)} "
       // binding.tv2.text = lfmt

    }


private fun printTimeFromJD(myTime:TJD): String{
    val lnDate = getDateFromJD(myTime + StoreVals.LOCAL_JMT)
    return lnDate.hours.toString() + ":" + lnDate.minutes.toString()
}




} // CLASS =========================================================================================
