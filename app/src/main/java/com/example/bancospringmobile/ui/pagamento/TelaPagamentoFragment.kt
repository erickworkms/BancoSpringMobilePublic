package com.example.bancospringmobile.ui.pagamento

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosPagamento
import com.example.bancospringmobile.network.metodos.AcessoRedePagamento
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaPagamentoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_pagamento, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        val pagarbotao = rootView.findViewById<Button>(R.id.botao_pagar)

        val contexto = requireContext()
        val preferencias = contexto.getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE)
        val token = preferencias.getString("cache-login", null)

        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Pagamento_to_Tela_Sessao)
        }

        val paginaAlerta = AlertDialog.Builder(contexto)
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()

        pagarbotao.setOnClickListener {
            pagarbotao.isEnabled = false;
            val classe = AcessoRedePagamento()

            val dadosPagamento = DadosPagamento(
                rootView.findViewById<EditText>(R.id.campo_pagamento).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_numeroCodigo).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_valorPagamento).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_vencimento).text.toString()
            )
            GlobalScope.launch {
                classe.fazerRequisicao(
                    token.toString(),
                    dadosPagamento,
                    requireContext(),
                    object : AuthCallback {
                        override fun onLoginSuccess() {
                            criaPopup.setTitle("Pagamento")
                            criaPopup.setMessage("Pagamento realizado com sucesso!")
                            criaPopup.show()
                            pagarbotao.isEnabled = true;
                        }

                        override fun onLoginFailure() {
                            criaPopup.setTitle("Pagamento")
                            criaPopup.setMessage("O pagamento falhou, tente novamente!")
                            criaPopup.show()
                            pagarbotao.isEnabled = true;
                        }
                    }
                )

            }
        }

        return rootView
    }
}