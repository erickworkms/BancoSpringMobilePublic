package com.example.bancospringmobile.ui.criacontas

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.metodos.AcessoRedeCriaConta
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaCriaContasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_cria_contas, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Conta_to_Tela_Sessao)
        }
        val criaContabotao = rootView.findViewById<Button>(R.id.botao_CriaConta)
        val OpcoesContabotao = rootView.findViewById<RadioGroup>(R.id.grupo_opcoes_conta)
        val tabelaContabotao = rootView.findViewById<TableLayout>(R.id.tabela_contas)

        val contexto = requireContext()
        val preferencias = contexto.getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE)
        val token = preferencias.getString("cache-login", null)

        val paginaAlerta = AlertDialog.Builder(contexto)
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()

        var opcoes = "0"
        OpcoesContabotao.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.campo_contaSalario -> {
                    opcoes = "2"
                }
                R.id.campo_contaPoupanca -> {
                    opcoes = "1"
                }
                R.id.campo_contaCorrente -> {
                    opcoes = "0"
                }
                // Adicione mais casos conforme necessário
            }
        }
        criaContabotao.setOnClickListener {
            criaContabotao.isEnabled = false;
            GlobalScope.launch {
                val classe = AcessoRedeCriaConta()
                classe.fazerRequisicao(opcoes,token.toString(),requireContext(),object : AuthCallback {
                    override fun onLoginSuccess() {
                        atualizaContasExistentes(token, tabelaContabotao)
                        criaPopup.setTitle("Criação de Conta")
                        criaPopup.setMessage("Conta criada com sucesso!")
                        criaPopup.show()
                        criaContabotao.isEnabled = true;
                    }

                    override fun onLoginFailure() {
                        criaPopup.setTitle("Criação de Conta")
                        criaPopup.setMessage("Dados invalidos")
                        criaPopup.show()
                        criaContabotao.isEnabled = true;
                    }
                })
            }
        }
        atualizaContasExistentes(token, tabelaContabotao)
        return rootView
    }

    private fun atualizaContasExistentes(token: String?, tabelaContabotao: TableLayout) {
        GlobalScope.launch {
            val classe = AcessoRedeCriaConta()
            classe.atualizarContas(token.toString(), tabelaContabotao, requireContext(), object : AuthCallback {
                override fun onLoginSuccess() {
                    Log.d("Usuario", "Atualização realizada")
                }

                override fun onLoginFailure() {
                    Log.d("Usuario", "A atualização falhou")
                }
            })
        }
    }
}