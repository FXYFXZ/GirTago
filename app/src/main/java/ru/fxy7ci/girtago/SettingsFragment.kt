package ru.fxy7ci.girtago
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.fxy7ci.girtago.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container, false)

        ControlsSetup()
        return binding.root
    }

    fun ControlsSetup(){

        binding.fldAltitude.setText("Кри")

//        binding.btnTry.setOnClickListener{
//            val catNames = arrayOf("+1", "+2", "+3", "+4","+5","+6","+7")
//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Выберите кота")
//                .setItems(catNames
//                ) { dialog, which ->
//                    binding.fldLongitude.text = catNames[which]
//                }
//            builder.create()
//            builder.show()
//        }



    }







} // CLASS =================================================================================