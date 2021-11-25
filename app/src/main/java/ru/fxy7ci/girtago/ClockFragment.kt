package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClockBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onResume() {
        binding.tvFirstFragment.text = "Hare Krishna"
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clockContainer.addView(MyRectangle(activity))
    }


    private class MyRectangle(context: Context?) : View(context) {
        var paint: Paint = Paint()
        override fun onDraw(canvas: Canvas) {
            paint.color = Color.GREEN
            val rect = Rect(100, 255, 200, 300)
            canvas.drawRect(rect, paint)
            paint.color = Color.BLUE
            canvas.drawCircle(255f,255f, 20f,paint)


        }
    }




}
