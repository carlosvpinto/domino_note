package com.carlosvpinto.anotardomino.fragments

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.carlosvpinto.anotardomino.R
import com.carlosvpinto.anotardomino.databinding.FragmentFelicitacionesBinding
import com.carlosvpinto.anotardomino.databinding.FragmentNombreJugadorBinding
import com.carlosvpinto.anotardomino.ui.home.HomeFragment

import android.media.MediaPlayer
import com.airbnb.lottie.LottieAnimationView


class FelicitacionesFragment : Fragment() {

    var listener: HomeFragment.OnNumberEnteredListener? = null


    private var _binding: FragmentFelicitacionesBinding? = null


    private val binding get() = _binding!!
    private var fila: Int? = 0



    fun returnNombre(nombre:String, fila: Int) {
        listener?.onNombreEntered(nombre,fila)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentFelicitacionesBinding.inflate(inflater, container, false)
        val root: View = binding.root



        // Iniciar la animación
        //binding.aniGanar.playAnimation()


        // Obtener los datos del Bundle
        fila = arguments?.getInt("fila")
        val nombre = arguments?.getString("nombre")
        val montoGanador = arguments?.getInt("monto")


        binding.txtNombreJugador.text= nombre
        binding.txtCantidad.text= "${montoGanador.toString()} Pts"

        //reproduse Sonido Victoria
        reproducirSonidoCorto(requireContext())

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_felicitaciones, container, false)
        binding.btnAceptar.setOnClickListener {



            cerrarFragmentoConAnimacion()
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    fun reproducirSonidoCorto(context: Context) {

        // Obtener el AudioManager
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Cargar el archivo de audio raw como recurso
        val soundId = R.raw.sonidoganar

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


    private fun cerrarFragmentoConAnimacion() {
        var animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // En el fragmento anterior (antes de cerrarlo)


                parentFragmentManager.beginTransaction()
                    .remove(this@FelicitacionesFragment)
                    .commitAllowingStateLoss()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view?.startAnimation(animation)
    }



}