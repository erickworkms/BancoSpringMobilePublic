package com.example.bancospringmobile.ui.telalogin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.metodos.AcessoRedeLogin
import com.example.bancospringmobile.network.AuthCallback
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TelaLoginFragment : Fragment() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_login, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Login_to_Tela_Inicial)
        }
        val logarbotao = rootView.findViewById<Button>(R.id.botao_logar)

        val paginaAlerta = AlertDialog.Builder(requireContext())
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()
        logarbotao.setOnClickListener {
            logarbotao.isEnabled = false;
            val classe = AcessoRedeLogin()
            GlobalScope.launch {
                classe.fazerRequisicao(
                    rootView.findViewById<EditText>(R.id.campo_usuario).text.toString(),
                    rootView.findViewById<EditText>(R.id.campo_senha).text.toString(),
                    requireContext(),object : AuthCallback {
                        override fun onLoginSuccess() {
                            logarbotao.isEnabled = true;
                            criaPopup.setTitle("Login")
                            criaPopup.setMessage("Login realizado com sucesso!")
                            criaPopup.show()
                                findNavController().navigate(R.id.action_Tela_Login_to_autenticacao_login)
                        }

                        override fun onLoginFailure() {
                            logarbotao.isEnabled = true;
                            criaPopup.setTitle("Login")
                            criaPopup.setMessage("Usuario ou senha invalidos")
                            criaPopup.show()
                        }
                    }
                )

            }

        }
        return rootView
    }
}