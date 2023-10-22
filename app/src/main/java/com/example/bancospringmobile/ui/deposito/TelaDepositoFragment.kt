package com.example.bancospringmobile.ui.deposito

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosDeposito
import com.example.bancospringmobile.network.metodos.AcessoRedeDepositos
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaDepositoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_deposito, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        val depositobotao = rootView.findViewById<Button>(R.id.botao_depositar)

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
            findNavController().navigate(R.id.action_Tela_Deposito_to_Tela_Sessao)
        }

        depositobotao.setOnClickListener {
            depositobotao.isEnabled = false;
            val classe = AcessoRedeDepositos()

            val dadosDeposito = DadosDeposito(
                rootView.findViewById<EditText>(R.id.campo_dataDeposito).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_numeroConta).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_agenciaDeposito).text.toString(),
                rootView.findViewById<Spinner>(R.id.campo_tipoConta).selectedItem.toString(),
                rootView.findViewById<EditText>(R.id.campo_cpfBeneficiario).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_valor).text.toString()
            )
            GlobalScope.launch {
                classe.fazerRequisicao(
                    token.toString(),
                    dadosDeposito,
                    requireContext(),
                    object : AuthCallback {
                        override fun onLoginSuccess() {
                            criaPopup.setTitle("Depósito")
                            criaPopup.setMessage("Depósito realizado com sucesso!")
                            criaPopup.show()
                            depositobotao.isEnabled = true;
                        }

                        override fun onLoginFailure() {
                            criaPopup.setTitle("Depósito")
                            criaPopup.setMessage("Dados invalidos")
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