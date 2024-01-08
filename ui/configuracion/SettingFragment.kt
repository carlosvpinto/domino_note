package com.carlosvpinto.anotardomino.ui.configuracion

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.carlosvpinto.anotardomino.R
import com.carlosvpinto.anotardomino.databinding.FragmentGalleryBinding
import com.carlosvpinto.anotardomino.databinding.FragmentSettingBinding
import com.carlosvpinto.anotardomino.ui.home.HomeFragment

class SettingFragment : Fragment() {

    private val PREFS_PUNTOS = "TotalFile"
    private val SELECTED_NUMBER = "selectedNumber"

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton50: RadioButton
    private lateinit var radioButton75: RadioButton
    private lateinit var radioButton100: RadioButton
    private lateinit var radioButton200: RadioButton

    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtener una referencia a las preferencias compartidas
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(PREFS_PUNTOS, Context.MODE_PRIVATE)
        val savedNumber: Int = getSelectedNumber(sharedPreferences)
        activarRadioButon(savedNumber)
        // Obtener referencias a RadioGroup y RadioButtons en tu diseño
        radioGroup = root.findViewById(R.id.radioGrup)
        radioButton50 = root.findViewById(R.id.radio50)
        radioButton75 = root.findViewById(R.id.radio75)
        radioButton100 = root.findViewById(R.id.radio100)
        radioButton200 = root.findViewById(R.id.radio200)

        // Configurar un listener para el RadioGroup
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Determinar cuál RadioButton está seleccionado
            val radioButtonSeleccionado = when (checkedId) {
                R.id.radio50 -> radioButton50
                R.id.radio75 -> radioButton75
                R.id.radio100 -> radioButton100
                R.id.radio200 -> radioButton200
                else -> null
            }

            // Hacer algo con el RadioButton seleccionado
            if (radioButtonSeleccionado != null && radioButtonSeleccionado.isChecked) {
                // El RadioButton seleccionado está marcado
                val puntuacion= radioButtonSeleccionado.text.toString()
                // Convertir el texto a un número (en este caso, es una cadena, puedes cambiarlo según tus necesidades)
                val selectedNumber: Int = puntuacion.toIntOrNull() ?: 0

                // Guardar el número en las preferencias compartidas
                saveSelectedNumber(sharedPreferences, selectedNumber)

            }

        }

//        val textView: TextView = binding.textGallery
//        settingViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        
        binding.btnAceptarConf.setOnClickListener {
            // Recuperar el número guardado y realizar acciones según sea necesario
            val savedNumber: Int = getSelectedNumber(sharedPreferences)
            cerrarFragment(this)
            // Haz lo que necesites con el número guardado...
            //Toast.makeText(requireContext(), "Numero de Puntuacion savedNumber $savedNumber", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun activarRadioButon(savedNumber: Int) {
    if (savedNumber==50)binding.radio50.isChecked= true
    if (savedNumber== 75)binding.radio75.isChecked= true
    if (savedNumber== 100)binding.radio100.isChecked= true
    if (savedNumber== 200)binding.radio200.isChecked= true

    }

    fun saveSelectedNumber(sharedPreferences: SharedPreferences, selectedNumber: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(SELECTED_NUMBER, selectedNumber)
        editor.apply()
    }
fun getSelectedNumber(sharedPreferences: SharedPreferences): Int {
        return sharedPreferences.getInt(SELECTED_NUMBER, 75)
    }

    fun cerrarFragment(fragment: Fragment) {
        // Verificar si el fragmento está asociado con un FragmentManager antes de realizar operaciones
        if (fragment.isAdded) {
            val fragmentManager = fragment.requireFragmentManager()

            // Verificar si hay fragmentos en la pila de retroceso antes de hacer pop
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
            } else {
                // Si no hay fragmentos en la pila de retroceso, simplemente remueve el fragmento
                fragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}