package com.carlosvpinto.anotardomino.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

import com.carlosvpinto.anotardomino.R
import com.carlosvpinto.anotardomino.databinding.FragmentLlenarDatosBinding
import com.carlosvpinto.anotardomino.ui.home.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment




class Llenardatos : BottomSheetDialogFragment() {

    var listener: HomeFragment.OnNumberEnteredListener? = null


    private var _binding: FragmentLlenarDatosBinding? = null


    private val binding get() = _binding!!
    private var nombre: String? = null
    private var fila: Int? = 0
    private var monto = 0


    fun returnNumber(number: Int, fila: Int) {
        listener?.onNumberEntered(number,fila)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentLlenarDatosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        // Obtener los datos del Bundle
        fila = arguments?.getInt("fila")
        val nombre = arguments?.getString("nombre")


        binding.txtNombreJugador.text= nombre

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_llenar_datos, container, false)
        binding.btnAceptar.setOnClickListener {
            val number = binding.editCantidad.text.toString().toIntOrNull() ?: 0


            returnNumber(number,fila!!)


            cerrarFragmentoConAnimacion()
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Solicitar foco
        binding.editCantidad.requestFocus()

        // Abrir teclado numérico
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editCantidad, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun cerrarFragmentoConAnimacion() {
        var animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                parentFragmentManager.beginTransaction()
                    .remove(this@Llenardatos)
                    .commitAllowingStateLoss()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view?.startAnimation(animation)
    }



}