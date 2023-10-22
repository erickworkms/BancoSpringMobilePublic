package com.example.bancospringmobile.ui.telacadastro

import android.app.AlertDialog
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
import com.example.bancospringmobile.network.DadosCadastro
import com.example.bancospringmobile.network.metodos.AcessoRedeCadastro
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaCadastroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_cadastro, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        val cadastrarbotao = rootView.findViewById<Button>(R.id.botao_cadastrar)
        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Cadastro_to_Tela_Inicial)
        }

        val paginaAlerta = AlertDialog.Builder(requireContext())
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()

        val campoNome = rootView.findViewById<EditText>(R.id.campo_nome)
        val campoDataNascimento = rootView.findViewById<EditText>(R.id.campo_dataNascimento)
        val campoCpf = rootView.findViewById<EditText>(R.id.campo_cpf)
        val campoEndereco = rootView.findViewById<EditText>(R.id.campo_endereco)
        val campoTelefone = rootView.findViewById<EditText>(R.id.campo_telefone)
        val campoEmail = rootView.findViewById<EditText>(R.id.campo_email)
        val campoUsuario = rootView.findViewById<EditText>(R.id.campo_usuarioCadastro)
        val campoSenha = rootView.findViewById<EditText>(R.id.campo_senhaCadastro)


        cadastrarbotao.setOnClickListener {
            cadastrarbotao.isEnabled = false;
            val classe = AcessoRedeCadastro()
            val dadosCadastro = DadosCadastro(
                campoNome.text.toString(),
                campoDataNascimento.text.toString(),
                campoCpf.text.toString(),
                campoEndereco.text.toString(),
                campoTelefone.text.toString(),
                campoEmail.text.toString(),
                campoUsuario.text.toString(),
                campoSenha.text.toString()
            )
            GlobalScope.launch {
                classe.fazerRequisicao(
                    dadosCadastro,
                    requireContext(),
                    object : AuthCallback {
                        override fun onLoginSuccess() {
                            criaPopup.setTitle("Cadastro")
                            criaPopup.setMessage("Cadastro realizado com sucesso!")
                            criaPopup.show()
                            cadastrarbotao.isEnabled = true;
                            findNavController().navigate(R.id.action_Tela_Cadastro_to_Tela_Inicial)
                        }

                        override fun onLoginFailure() {
                            criaPopup.setTitle("Cadastro")
                            criaPopup.setMessage("Ocorreu um erro em seu cadastro, tente novamente")
                            criaPopup.show()
                            cadastrarbotao.isEnabled = true;
                        }
                    }
                )

            }
        }
        return rootView
    }
}