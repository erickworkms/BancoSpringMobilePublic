package com.example.bancospringmobile.ui.telainicial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R


class TelaInicialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_inicial,container,false)
        val loginbotao = rootView.findViewById<Button>(R.id.login_botao)

        loginbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Inicial_to_Tela_Login)
        }
        val cadastrobotao = rootView.findViewById<Button>(R.id.voltar_botao)

        cadastrobotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Inicial_to_Tela_Cadastro)
        }
        return rootView
    }
}