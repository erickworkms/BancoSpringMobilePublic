package com.example.bancospringmobile.ui.atualiza

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosCadastro
import com.example.bancospringmobile.network.metodos.AcessoRedeAtualiza
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaAtualizaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_atualiza_cadastro, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        val atualizabotao = rootView.findViewById<Button>(R.id.botao_atualizar)

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
            findNavController().navigate(R.id.action_Tela_Atualiza_to_Tela_Sessao)
        }

        GlobalScope.launch() {
            val classe = AcessoRedeAtualiza()
            classe.recebeCadastro(token.toString(), requireContext(), object : AuthCallback {
                override fun onLoginSuccess() {
                    val nomeCampo = rootView.findViewById<TextView>(R.id.campo_nomeCadastro)
                    nomeCampo.text = classe.resposta.nome
                    val dataNascCampo = rootView.findViewById<TextView>(R.id.campo_dataNascCadastro)
                    dataNascCampo.text = classe.resposta.dataNascimento
                    val cpfCampo = rootView.findViewById<TextView>(R.id.campo_cpfCadastro)
                    cpfCampo.text = classe.resposta.cpf
                    val enderecoCampo = rootView.findViewById<TextView>(R.id.campo_enderecoCadastro)
                    enderecoCampo.text = classe.resposta.endereco
                    val telefoneCampo = rootView.findViewById<TextView>(R.id.campo_telefone)
                    telefoneCampo.text = classe.resposta.telefone
                    val emailCampo = rootView.findViewById<TextView>(R.id.campo_email)
                    emailCampo.text = classe.resposta.email
                    val usuarioCampo = rootView.findViewById<TextView>(R.id.campo_usuarioAtualizaCadastro)
                    usuarioCampo.text = classe.resposta.usuario
                    val senhaCampo = rootView.findViewById<TextView>(R.id.campo_senhaAtualizaCadastro)
                    senhaCampo.text = classe.resposta.senha
                    Log.d("Usuario", " usuario cadastrado")
                }

                override fun onLoginFailure() {
                    Log.d("Usuario", " passou no false")
                }
            })
        }

        atualizabotao.setOnClickListener {
            atualizabotao.isEnabled = false;
            val classe = AcessoRedeAtualiza()

            val dadosCadastro = DadosCadastro(
                rootView.findViewById<EditText>(R.id.campo_nomeCadastro).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_dataNascCadastro).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_cpfCadastro).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_enderecoCadastro).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_telefone).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_email).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_usuarioAtualizaCadastro).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_senhaAtualizaCadastro).text.toString()
            )
            GlobalScope.launch {
                classe.atualizaCadastro(
                    token.toString(),
                    dadosCadastro,
                    requireContext(),
                    object : AuthCallback {
                        override fun onLoginSuccess() {
                            criaPopup.setTitle("Atualização Cadastral")
                            criaPopup.setMessage("Atualização realizada com sucesso!")
                            criaPopup.show()
                            atualizabotao.isEnabled = true;
                        }

                        override fun onLoginFailure() {
                            criaPopup.setTitle("Atualização Cadastral")
                            criaPopup.setMessage("A atualização falhou, tente novamente!")
                            criaPopup.show()
                            atualizabotao.isEnabled = true;
                        }
                    }
                )

            }
        }

        return rootView
    }
}