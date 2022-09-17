package ru.fxy7ci.girtago
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
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
        setHasOptionsMenu (true)
        return binding.root
    }

    fun ControlsSetup(){
        binding.fldAltitude.setText("Кри")
        binding.btnZone.setOnClickListener{
            val catNames = arrayOf("+1", "+2", "+3", "+4","+5","+6","+7")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Выберите кота")
                .setItems(catNames
                ) { dialog, which ->
                    binding.fldAltitude.setText(catNames[which])
                }
            builder.create()
            builder.show()
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }





} // CLASS =================================================================================