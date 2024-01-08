package com.carlosvpinto.anotardomino.fragments

import android.content.Context
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
import com.carlosvpinto.anotardomino.databinding.FragmentLlenarDatosBinding
import com.carlosvpinto.anotardomino.databinding.FragmentNombreJugadorBinding
import com.carlosvpinto.anotardomino.ui.home.HomeFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NombreJugadorFragment : Fragment() {
    var listener: HomeFragment.OnNumberEnteredListener? = null


    private var _binding: FragmentNombreJugadorBinding? = null


    private val binding get() = _binding!!
    private var nombre: String? = null
    private var fila: Int? = 0
    private var monto = 0


    fun returnNombre(nombre:String, fila: Int) {
        listener?.onNombreEntered(nombre,fila)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentNombreJugadorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtener los datos del Bundle
        fila = arguments?.getInt("fila")
        val nombre = arguments?.getString("nombre")


        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_llenar_datos, container, false)
        binding.btnAceptar.setOnClickListener {
            val nombre = binding.editNombre.text.toString()


            returnNombre(nombre,fila!!)


            cerrarFragmentoConAnimacion()
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Solicitar foco
        binding.editNombre.requestFocus()

        // Abrir teclado numérico
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editNombre, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun cerrarFragmentoConAnimacion() {
        var animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                parentFragmentManager.beginTransaction()
                    .remove(this@NombreJugadorFragment)
                    .commitAllowingStateLoss()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view?.startAnimation(animation)
    }
}