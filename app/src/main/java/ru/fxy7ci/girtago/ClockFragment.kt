package ru.fxy7ci.girtago

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fxy7ci.girtago.AstroLib.StarTimes
import ru.fxy7ci.girtago.databinding.FragmentClockBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class ClockFragment : Fragment(R.layout.fragment_clock) {
    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!
    private lateinit var myClock : MyClock

    private val starTimes = StarTimes()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClockBinding.inflate(inflater,container, false)

        binding.btnNext.setOnClickListener {
            myClock.setMyDate(2f)
        }
        // Инициализация расчета
        starTimes.Noon = 0.22


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


    private fun printDates() {
        val currentDate = Date()
// Форматирование времени как "день.месяц.год"
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)

// Форматирование времени как "часы:минуты:секунды"
//        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//        val timeText: String = timeFormat.format(currentDate)

        binding.tvDateTime.text = dateText
        binding.tvJDay.text = 151654654.toString()
    }



} // CLASS =========================================================================================
