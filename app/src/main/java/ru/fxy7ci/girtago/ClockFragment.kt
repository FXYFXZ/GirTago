package ru.fxy7ci.girtago

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.fxy7ci.girtago.databinding.FragmentClockBinding


/**
 * A simple [Fragment] subclass.
 */
class ClockFragment : Fragment(R.layout.fragment_clock) {
    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!
    lateinit var myView : MyRectangle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClockBinding.inflate(inflater,container, false)

        binding.btnNext.setOnClickListener(View.OnClickListener {
            myView.radius = 50f
            myView.invalidate()
//            myView.visibility = View.GONE
        })


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
        myView = MyRectangle(activity)
        binding.clockContainer.addView(myView)
    }


    class MyRectangle(context: Context?) : View(context) {
        var paint: Paint = Paint()
        var centerX = 255f
        var centerY = 255f
        var radius = 20f

        override fun onDraw(canvas: Canvas) {
            paint.color = Color.GREEN
            val rect = Rect(100, 255, 200, 300)
            canvas.drawRect(rect, paint)
            paint.color = Color.BLUE
            canvas.drawCircle(centerX,centerY, radius,paint)


        }

        fun setMyDate(){
            radius= 50f
        }


    }



}
