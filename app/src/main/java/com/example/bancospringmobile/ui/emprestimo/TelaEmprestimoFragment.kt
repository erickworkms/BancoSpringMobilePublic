package com.example.bancospringmobile.ui.emprestimo

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
import com.example.bancospringmobile.network.DadosEmprestimo
import com.example.bancospringmobile.network.metodos.AcessoRedeEmprestimo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaEmprestimoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_emprestimo, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        val depositobotao = rootView.findViewById<Button>(R.id.botao_receberEmprestimo)

        val contexto = requireContext()
        val preferencias = contexto.getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE)
        val token = preferencias.getString("cache-login", null)

        val paginaAlerta = AlertDialog.Builder(contexto)
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()

        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Emprestimo_to_Tela_Sessao)
        }

        depositobotao.setOnClickListener {
            depositobotao.isEnabled = false;
            val classe = AcessoRedeEmprestimo()

            val dadosEmprestimo = DadosEmprestimo(
                rootView.findViewById<EditText>(R.id.campo_dataEmprestimo).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_valorEmprestimo).text.toString()
            )
            GlobalScope.launch {
                classe.fazerRequisicao(
                    token.toString(),
                    dadosEmprestimo,
                    requireContext(),
                    object : AuthCallback {
                        override fun onLoginSuccess() {
                            criaPopup.setTitle("Empréstimo")
                            criaPopup.setMessage("Empréstimo recebido com sucesso!")
                            criaPopup.show()
                            depositobotao.isEnabled = true;
                        }

                        override fun onLoginFailure() {
                            criaPopup.setTitle("Empréstimo")
                            criaPopup.setMessage("Empréstimo falhou,tente novamente!")
                            criaPopup.show()
                            depositobotao.isEnabled = true;
                        }
                    }
                )

            }
        }
        return rootView
    }
}