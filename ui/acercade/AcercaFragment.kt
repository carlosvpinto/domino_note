package com.carlosvpinto.anotardomino.ui.acercade

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.carlosvpinto.anotardomino.R
import com.carlosvpinto.anotardomino.databinding.FragmentAcercaBinding

import com.carlosvpinto.anotardomino.ui.home.SharedViewModel

class AcercaFragment : Fragment() {

    private var _binding: FragmentAcercaBinding? = null


    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(SharedViewModel::class.java)

        _binding = FragmentAcercaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textoHtml = getString(R.string.texto_largo_con_formato)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Si es Android N o superior, utiliza Html.fromHtml con FROM_HTML_MODE_COMPACT
            binding.textViewScrollable.text = Html.fromHtml(textoHtml, Html.FROM_HTML_MODE_COMPACT)
        } else {
            // Si es inferior a Android N, utiliza Html.fromHtml sin FROM_HTML_MODE_COMPACT
            @Suppress("DEPRECATION")
            binding.textViewScrollable.text = Html.fromHtml(textoHtml)
        }

        // Permite que los enlaces en el texto sean clicables
        binding.textViewScrollable.movementMethod = LinkMovementMethod.getInstance()

        binding.btnCalificar.setOnClickListener{
            abrirCalificacionEnPlayStore(requireContext())
        }

        return root
    }

    //PARA CALIFICAR LA APP EN PLAYSTORE
    fun abrirCalificacionEnPlayStore(context: Context) {
        try {
            val packageName = context.packageName
            val marketIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            context.startActivity(marketIntent)
        } catch (e: ActivityNotFoundException) {
            // Si no hay Play Store instalada, abrir en el navegador
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
            )
            context.startActivity(webIntent)
        }
    }
}

