package ru.fxy7ci.girtago

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fxy7ci.girtago.databinding.FragmentClockBinding


/**
 * A simple [Fragment] subclass.
 */
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
        return binding.root
    }

    override fun onResume() {
        binding.tvFirstFragment.text = "26.11.2021"
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

}
