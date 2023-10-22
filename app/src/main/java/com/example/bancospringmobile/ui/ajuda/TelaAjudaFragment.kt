package com.example.bancospringmobile.ui.ajuda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R

class TelaAjudaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_ajuda, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Ajuda_to_Tela_Sessao)
        }
        return rootView
    }
}