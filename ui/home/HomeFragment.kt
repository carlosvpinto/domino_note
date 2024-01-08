package com.carlosvpinto.anotardomino.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider


import com.carlosvpinto.anotardomino.fragments.Llenardatos
import com.carlosvpinto.anotardomino.R
import com.carlosvpinto.anotardomino.databinding.FragmentHomeBinding
import com.carlosvpinto.anotardomino.fragments.FelicitacionesFragment
import com.carlosvpinto.anotardomino.fragments.NombreJugadorFragment

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.viewModels

class HomeFragment : Fragment(){


    private val PREFS_PUNTOS = "TotalFile"
    private val SELECTED_NUMBER = "selectedNumber"
    private var TotalGanar = 0
    // Variables para almacenar el estado de los EditText y otros elementos
    private var editTextValues1: Array<String?> = arrayOfNulls(15)
    private var editTextValues2: Array<String?> = arrayOfNulls(15)
    // Variables para almacenar el estado de los LinearLayout
    private var linearLayoutVisibilities: BooleanArray = booleanArrayOf()

    private val sharedViewModel: SharedViewModel by viewModels()



    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!
    // Variables para almacenar el estado de visibilidad y los valores de los textos
    private var estadosVisibilidad: BooleanArray = booleanArrayOf()
    private var valoresText: Array<String?> = arrayOfNulls(15)

        val layoutIds = arrayOf(
        R.id.lnerLine1, R.id.lnerLine2, R.id.lnerLine3, R.id.lnerLine4,
        R.id.lnerLine5, R.id.lnerLine6, R.id.lnerLine7, R.id.lnerLine8,
        R.id.lnerLine9, R.id.lnerLine10, R.id.lnerLine10, R.id.lnerLine11,
        R.id.lnerLine12, R.id.lnerLine13, R.id.lnerLine14, R.id.lnerLine15

    )
    val textosJgd1Ids = arrayOf(
        R.id.txtMontoJugador1_1,
        R.id.txtMontoJugador1_2,
        R.id.txtMontoJugador1_3,
        R.id.txtMontoJugador1_4,
        R.id.txtMontoJugador1_5,
        R.id.txtMontoJugador1_6,
        R.id.txtMontoJugador1_7,
        R.id.txtMontoJugador1_8,
        R.id.txtMontoJugador1_9,
        R.id.txtMontoJugador1_10,
        R.id.txtMontoJugador1_11,
        R.id.txtMontoJugador1_12,
        R.id.txtMontoJugador1_13,
        R.id.txtMontoJugador1_14,
        R.id.txtMontoJugador1_15,
    )
    val textosJgd2Ids = arrayOf(
        R.id.txtMontoJugador2_1,
        R.id.txtMontoJugador2_2,
        R.id.txtMontoJugador2_3,
        R.id.txtMontoJugador2_4,
        R.id.txtMontoJugador2_5,
        R.id.txtMontoJugador2_6,
        R.id.txtMontoJugador2_7,
        R.id.txtMontoJugador2_8,
        R.id.txtMontoJugador2_9,
        R.id.txtMontoJugador2_10,
        R.id.txtMontoJugador2_11,
        R.id.txtMontoJugador2_12,
        R.id.txtMontoJugador2_13,
        R.id.txtMontoJugador2_14,
        R.id.txtMontoJugador2_15
    )
    val BotonesElimIds = arrayOf(
        R.id.imgEliminar,
        R.id.imgEliminar2,
        R.id.imgEliminar3,
        R.id.imgEliminar4,
        R.id.imgEliminar5,
        R.id.imgEliminar6,
        R.id.imgEliminar7,
        R.id.imgEliminar8,
        R.id.imgEliminar9,
        R.id.imgEliminar10,
        R.id.imgEliminar11,
        R.id.imgEliminar12,
        R.id.imgEliminar13,
        R.id.imgEliminar14,
        R.id.imgEliminar15
    )
    interface OnNumberEnteredListener {
        fun onNumberEntered(number: Int, fila: Int)
        fun onNombreEntered(nombre: String, fila: Int)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(SharedViewModel::class.java)
        // Restaurar estados de visibilidad desde el savedInstanceState
        estadosVisibilidad = savedInstanceState?.getBooleanArray("estados_visibilidad") ?: booleanArrayOf()


        // Obtener una referencia a las preferencias compartidas
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(PREFS_PUNTOS, Context.MODE_PRIVATE)
        TotalGanar = getSelectedNumber(sharedPreferences)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnReiniciar.setOnClickListener {
            limpiartxt()
        }

        binding.fabjugador2.setOnClickListener {
            val nombre = binding.fabjugador1.text.toString()
            val fila = 2
            llenarDatosNombre(nombre, fila)
        }

        binding.fabjugador1.setOnClickListener {
            val nombre = binding.fabjugador1.text.toString()
            val fila = 1
            llenarDatosNombre(nombre, fila)
        }

        binding.btnAdd1.setOnClickListener {
            val nombre = binding.fabjugador1.text.toString()
            val fila = 1
            llenarDatosFun(nombre, fila)
        }
        binding.btnAdd2.setOnClickListener {
            val nombre = binding.fabjugador2.text.toString()
            val fila = 2
            llenarDatosFun(nombre, fila)
        }
        binding.txtTotal1.setOnClickListener {
            val nombre = binding.fabjugador1.text.toString()
            val fila = 1
            llenarDatosFun(nombre, fila)
        }

        binding.txtTotal2.setOnClickListener {
            val nombre = binding.fabjugador2.text.toString()
            val fila = 2
            llenarDatosFun(nombre, fila)
        }



        return root

    }


    fun getSelectedNumber(sharedPreferences: SharedPreferences): Int {
        return sharedPreferences.getInt(SELECTED_NUMBER, 75 )
    }

//    //PARA RECUPERAR LOS DATOS CUANDO SE SALE DEL FRAGMNET********************
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.d("LlenarDatos", "onSaveInstanceState: onSaveInstanceState ENTROOOO")
//        // Guardar valores de los EditText
//        for (i in 0 until 15) {
//            val editText = view?.findViewById<TextView>(textosJgd1Ids[i])
//            editTextValues1[i] = editText?.text.toString()
//
//        }
//
//        // Guardar valores de los EditText
//        for (i in 0 until 15) {
//            val editText = view?.findViewById<TextView>(textosJgd2Ids[i])
//            editTextValues2[i] = editText?.text.toString()
//
//        }
//
//        // Guardar el estado de visibilidad de los LinearLayout
//        for (i in 0 until 15) {
//            val linearLayout = view?.findViewById<LinearLayout>(layoutIds[i])
//            linearLayoutVisibilities[i] = linearLayout?.visibility == View.VISIBLE
//        }
//
//        // Guardar otros elementos visuales
//        // Por ejemplo, si tienes elementos visuales adicionales, guárdalos aquí
//
//        outState.putStringArray("editTextValues1", editTextValues1)
//        outState.putStringArray("editTextValues2", editTextValues2)
//        outState.putBooleanArray("linearLayoutVisibilities", linearLayoutVisibilities)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.d("LlenarDatos", "onViewCreated: onViewCreated ENTROOOO")
//        // Restaurar el estado de visibilidad de los LinearLayout
//        val linearLayoutVisibilitiesSaved = savedInstanceState?.getBooleanArray("linearLayoutVisibilities")
//        for (i in 0 until 15) {
//            val linearLayout = view.findViewById<LinearLayout>(layoutIds[i])
//            linearLayout?.visibility = if (linearLayoutVisibilitiesSaved?.get(i) == true) View.VISIBLE else View.GONE
//        }
//
//        // Restaurar valores de los EditText
//        val editTextValuesSaved1 = savedInstanceState?.getStringArray("editTextValues1")
//        val editTextValuesSaved2 = savedInstanceState?.getStringArray("editTextValues2")
//        for (i in 0 until 15) {
//            val editText = view.findViewById<TextView>(textosJgd1Ids[i])
//            editText?.text=editTextValuesSaved1?.get(i)
//        }
//
////         ... (otros códigos)
//    }
//********************************RECUPERA****************



    fun limpiartxt() {


        // Tu lógica para limpiar los textos y establecer la visibilidad en GONE
        // Asegúrate de que la vista esté adjunta antes de acceder a los elementos de vista

        view?.let { view ->
            // Itera sobre los IDs de los layouts y establece la visibilidad en GONE
            for (layoutId in layoutIds) {

                val layout = view.findViewById<LinearLayout>(layoutId)
                layout?.visibility = View.GONE
            }

            // Itera sobre los IDs de los textos del jugador 1 y establece el texto en "0"
            for (textJgd1Id in textosJgd1Ids) {
                val textView = view.findViewById<TextView>(textJgd1Id)
                textView?.text = "0"
            }

            // Itera sobre los IDs de los textos del jugador 2 y establece el texto en "0"
            for (textJgd2Id in textosJgd2Ids) {
                val textView = view.findViewById<TextView>(textJgd2Id)
                textView?.text = "0"
            }

            // Otras acciones que necesites realizar al limpiar los textos
            binding.txtTotal1.text= "0"
            binding.txtTotal2.text= "0"
        }
    }
    private fun activarBtnEliminar() {
        view?.let { view ->
            // Itera sobre los IDs de los layouts y establece la visibilidad en GONE
            for (botonId in BotonesElimIds) {
                val botonElim = view.findViewById<ImageButton>(botonId)
                botonElim?.setOnClickListener {
                    EliminarUltimoLayout()
                    //reproduse Sonido Victoria
                    reproducirSonidoCorto(requireContext())
                }
            }
        }
    }



    private fun llenarDatosFun(nombre: String, fila: Int) {
        val fragmentTag = "LLenardatos"

        var existingFragment = childFragmentManager.findFragmentByTag(fragmentTag)

        if(existingFragment!=null){
            childFragmentManager.beginTransaction().remove(existingFragment).commit()
            existingFragment= null
        }
        if (existingFragment == null) {
            val listener = object : OnNumberEnteredListener {
                override fun onNumberEntered(number: Int, fila: Int) {
                    // cargardatosLista(number)
                    mostrarSiguienteLayout(number, fila)
                }

                override fun onNombreEntered(nombre: String, fila: Int) {

                }

            }
            val fragment = Llenardatos()
            fragment.listener = listener
            //***************
            // Crear un Bundle y agregar los datos
            val bundle = Bundle()
            bundle.putInt("fila", fila)
            bundle.putString("nombre", nombre)
            // Asignar el Bundle al Fragmento B
            fragment.arguments = bundle
            // Esperar a que el fragmento padre haya pasado por onResume()
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer2, fragment, fragmentTag)
                .commitAllowingStateLoss()
        }
    }

    private fun llenarDatosNombre(nombre: String, fila: Int) {
        val fragmentTag = "NombreJugador"
        val existingFragment = childFragmentManager.findFragmentByTag(fragmentTag)
        if (existingFragment == null) {
            val listener = object : OnNumberEnteredListener {
                override fun onNumberEntered(number: Int, fila: Int) {

                }

                override fun onNombreEntered(nombre: String, fila: Int) {
                    if (!nombre.isNullOrEmpty()){
                        if (fila== 1){
                            binding.fabjugador1.text = nombre
                        }
                        if (fila==2){
                            binding.fabjugador2.text = nombre
                        }
                    }else {
                        if (fila == 1) {
                            binding.fabjugador1.text = "Jugador 1"
                        }
                        if (fila == 2) {
                            binding.fabjugador2.text = "Jugagador 2"
                        }
                    }

                }

            }
            val fragment = NombreJugadorFragment()
            fragment.listener = listener
            //***************
            // Crear un Bundle y agregar los datos
            val bundle = Bundle()
            bundle.putInt("fila", fila)
            bundle.putString("nombre", nombre)
            // Asignar el Bundle al Fragmento B
            fragment.arguments = bundle
            // Esperar a que el fragmento padre haya pasado por onResume()
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer2, fragment, fragmentTag)
                .commitAllowingStateLoss()
        }
    }


    private fun mostrarSiguienteLayout(number: Int, fila: Int) {
        // Encuentra el último LinearLayout visible
        var ultimoVisible = -1
        for (i in layoutIds.indices.reversed()) {
            val layout = view?.findViewById<LinearLayout>(layoutIds[i])
            if (layout?.visibility == View.VISIBLE) {
                ultimoVisible = i
                break
            }
        }

        // Muestra el siguiente LinearLayout
        val siguienteVisible = ultimoVisible + 1

        if (siguienteVisible < (layoutIds.size - 1)) {
            val text2siguiente = view?.findViewById<TextView>(textosJgd2Ids[siguienteVisible])
            val text1siguiente = view?.findViewById<TextView>(textosJgd1Ids[siguienteVisible])
            val layoutSiguiente = view?.findViewById<LinearLayout>(layoutIds[siguienteVisible])
            layoutSiguiente?.visibility = View.VISIBLE

            if (fila == 1) {
                text1siguiente?.text = number.toString()
            }
            if (fila == 2) {
                text2siguiente?.text = number.toString()
            }

            activarBtnEliminar()

            totalizar()

        } else {
            Toast.makeText(
                requireContext(),
                "Aprenda a JUGAR 15 partida para terminar... Fin DE LINEAS..",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun EliminarUltimoLayout() {
        // Encuentra el último LinearLayout visible
        var ultimoVisible = -1
        for (i in layoutIds.indices.reversed()) {
            val layout = view?.findViewById<LinearLayout>(layoutIds[i])
            if (layout?.visibility == View.VISIBLE) {
                ultimoVisible = i
                break
            }
        }

        // Muestra el siguiente LinearLayout
        val siguienteVisible = ultimoVisible

        if (siguienteVisible>=0){
            if (siguienteVisible < (layoutIds.size - 1)) {
                val text2siguiente = view?.findViewById<TextView>(textosJgd2Ids[siguienteVisible])
                val text1siguiente = view?.findViewById<TextView>(textosJgd1Ids[siguienteVisible])
                val layoutSiguiente = view?.findViewById<LinearLayout>(layoutIds[siguienteVisible])
                layoutSiguiente?.visibility = View.GONE


                text1siguiente?.text = "0"


                text2siguiente?.text = "0"




                totalizar()

            } else {
                Toast.makeText(
                    requireContext(),
                    "Ya  JUGO 15 partida para terminar... Fin DE LINEAS..",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun totalizar() {
        var gano = false
       var Total2 = 0
       var Total = 0
        var ultimoVisible = 0
        for (i in layoutIds.indices.reversed()) {
            val layout = view?.findViewById<LinearLayout>(layoutIds[i])
            if (layout?.visibility == View.VISIBLE) {
                val text1siguiente = view?.findViewById<TextView>(textosJgd1Ids[ultimoVisible])
                val text2siguiente = view?.findViewById<TextView>(textosJgd2Ids[ultimoVisible])
                var valor = text1siguiente?.text.toString().toIntOrNull() ?: 0
                var valor2 = text2siguiente?.text.toString().toIntOrNull() ?: 0

                Total2 += valor2
                Total += valor
                ultimoVisible = i
            }
        }

        if (Total>=TotalGanar){

            val nuevoFragmento = FelicitacionesFragment()
            limpiartxt()
            reemplazarFragmentEnContenedor(nuevoFragmento,Total,binding.fabjugador1.text.toString() )
            gano= true

        }
        if (Total2>=TotalGanar){
            limpiartxt()
            val nuevoFragmento = FelicitacionesFragment()
            reemplazarFragmentEnContenedor(nuevoFragmento,Total2,binding.fabjugador2.text.toString() )
            gano= true

        }
        if (gano== true){
            binding.txtTotal1.text = "0"
            binding.txtTotal2.text = "0"
        }else{
            binding.txtTotal1.text = Total.toString()
            binding.txtTotal2.text = Total2.toString()
        }


    }



    private fun inhabilitarNoturno() {
        // Cambia el modo nocturno directamente en la aplicación (puede afectar otras actividades/fragmentos)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Si deseas aplicar inmediatamente el cambio en este fragmento
        requireActivity().recreate()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun reemplazarFragmentEnContenedor(fragment: Fragment, monto: Int, nombre: String) {
        val fragmentManager = childFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // Cerrar el fragmento anterior si está en la pila de retroceso
        val fragmentAnterior = fragmentManager.findFragmentById(R.id.fragmentContainer2)
        if (fragmentAnterior != null) {
            transaction.remove(fragmentAnterior)
        }
        vibrar(requireContext())


        // Crear un Bundle y agregar los datos
        val bundle = Bundle()
        bundle.putInt("monto", monto)
        bundle.putString("nombre", nombre)

        // Asignar el Bundle al Fragmento B
        fragment.arguments = bundle
        // Reemplazar el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.fragmentContainer2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun vibrar(context: Context, duracion: Long = 1000) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Para dispositivos con Android 8.0 (Oreo) o superior
                val efecto = VibrationEffect.createOneShot(duracion, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(efecto)
            } else {
                // Para dispositivos anteriores a Android 8.0
                vibrator.vibrate(duracion)
            }
        }
    }
    fun reproducirSonidoCorto(context: Context) {

        // Obtener el AudioManager
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Cargar el archivo de audio raw como recurso
        val soundId = R.raw.mariotuberia

        // Crear un MediaPlayer
        val mediaPlayer = MediaPlayer.create(context, soundId)

        // Establecer volumen máximo
        mediaPlayer.setVolume(1.0f, 1.0f)
        // Reproducir sonido
        mediaPlayer.start()

        // Escuchar cuando finaliza y liberar recursos
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }

    }



}