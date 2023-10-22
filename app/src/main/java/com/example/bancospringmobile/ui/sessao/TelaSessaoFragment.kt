package com.example.bancospringmobile.ui.sessao

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.metodos.AcessoRedeSessao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaSessaoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_sessao, container, false)
        val contabotao = rootView.findViewById<ImageButton>(R.id.botao_conta)
        val classe = AcessoRedeSessao()

        val contexto = requireContext()
        val preferencias = contexto.getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE)
        val token = preferencias.getString("cache-login", null)

        val paginaAlerta = AlertDialog.Builder(contexto)
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()

        contabotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Conta)
        }
        val transferenciabotao = rootView.findViewById<ImageButton>(R.id.botao_transferencia)
        transferenciabotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Transferencia)
        }
        val pagamentobotao = rootView.findViewById<ImageButton>(R.id.botao_pagamento)
        pagamentobotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Pagamento)
        }
        val depositobotao = rootView.findViewById<ImageButton>(R.id.botao_deposito)
        depositobotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Deposito)
        }
        val atualizabotao = rootView.findViewById<ImageButton>(R.id.botao_atualizarDados)
        atualizabotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Atualiza)
        }
        val extratobotao = rootView.findViewById<ImageButton>(R.id.botao_extrato)
        extratobotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Extrato)
        }
        val emprestimobotao = rootView.findViewById<ImageButton>(R.id.botao_emprestimo)
        emprestimobotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Emprestimo)
        }
        val ajudabotao = rootView.findViewById<ImageButton>(R.id.botao_ajuda)
        ajudabotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Ajuda)
        }
        val deslogarbotao = rootView.findViewById<Button>(R.id.botao_deslogar)
        deslogarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Inicial)
        }
        GlobalScope.launch {
            classe.fazerRequisicao(token.toString(),requireContext(),object : AuthCallback {
                override fun onLoginSuccess() {
                    val usuarioCampo = rootView.findViewById<TextView>(R.id.resultado_usuario)
                    usuarioCampo.text = classe.resposta.usuario
                    val numeroCampo = rootView.findViewById<TextView>(R.id.resultado_numeroConta)
                    numeroCampo.text = classe.resposta.numero.toString()
                    val agenciaCampo = rootView.findViewById<TextView>(R.id.resultado_agencia_texto)
                    agenciaCampo.text = classe.resposta.agencia
                    val tipoContaCampo = rootView.findViewById<TextView>(R.id.resultado_tipoConta)
                    tipoContaCampo.text = classe.resposta.tipoConta
                    val saldoCampo = rootView.findViewById<TextView>(R.id.resultado_saldo)
                    saldoCampo.text = classe.resposta.saldo.toString()
                }

                override fun onLoginFailure() {
                    criaPopup.setTitle("Login")
                    criaPopup.setMessage("Ocorreu um erro na sua sessão, faça seu login novamente")
                    criaPopup.show()
                    findNavController().navigate(R.id.action_Tela_Sessao_to_Tela_Inicial)
                }
            })
        }
        return rootView
    }
}