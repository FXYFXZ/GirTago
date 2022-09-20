package ru.fxy7ci.girtago
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        val lst : MutableList<String> = ArrayList()
        lst.add("fsdfs1")
        lst.add("fsdfs2")
        lst.add("fsdfs3")
        lst.add("fsdfs4")
        lst.add("fsdfs5")


        binding.spZone.adapter = ArrayAdapter(this.requireActivity(), R.layout.spin_row, R.id.weekofday, lst)
        binding.spZone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity, type, Toast.LENGTH_LONG).show()
                println(type)
            }
        }

        return binding.root
    }

    fun ControlsSetup(){
        binding.fldAltitude.setText("Кри")
//        binding.btnZone.setOnClickListener{
//            val catNames = arrayOf("+1", "+2", "+3", "+4","+5","+6","+7")
//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Выберите кота")
//                .setItems(catNames
//                ) { dialog, which ->
//                    binding.fldAltitude.setText(catNames[which])
//                }
//            builder.create()
//            builder.show()
//        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }





} // CLASS =================================================================================