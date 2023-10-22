package com.example.bancospringmobile.ui.transferencia

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosTransferencia
import com.example.bancospringmobile.network.metodos.AcessoRedeTransferencia
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TelaTransferenciaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val classe = AcessoRedeTransferencia()

        val contexto = requireContext()
        val preferencias = contexto.getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE)
        val token = preferencias.getString("cache-login", null)

        val rootView = inflater.inflate(R.layout.pagina_transferencia, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)

        val paginaAlerta = AlertDialog.Builder(contexto)
        paginaAlerta.setView(R.layout.pagina_alerta)

        paginaAlerta.setPositiveButton("OK") { popUp, _ ->

            popUp.dismiss()
        }

        val criaPopup = paginaAlerta.create()

        voltarbotao.setOnClickListener {
            findNavController().navigate(R.id.action_Tela_Transferencia_to_Tela_Sessao)
        }
        val transferirbotao = rootView.findViewById<Button>(R.id.botao_transferir)
        transferirbotao.setOnClickListener {
            transferirbotao.isEnabled = false;
            val valorTipoConta = rootView.findViewById<Spinner>(R.id.campo_tipoConta).selectedItem.toString()
            val valorTipoContaFiltro =
                if (valorTipoConta == "Conta Corrente") "0" else if (valorTipoConta == "Conta Poupança") "2" else "1"

            val dadosTransferencia = DadosTransferencia(
                rootView.findViewById<TextView>(R.id.campo_dataPagamentoTransfer).text.toString(),

                rootView.findViewById<TextView>(R.id.campo_numeroConta).text.toString(),

                rootView.findViewById<TextView>(R.id.campo_agencia).text.toString(),

                valorTipoContaFiltro,

                rootView.findViewById<TextView>(R.id.campo_cpfBeneficiario).text.toString(),

                rootView.findViewById<TextView>(R.id.campo_valor).text.toString()
            )

            GlobalScope.launch {
                classe.fazerRequisicao(token.toString(), dadosTransferencia, requireContext(), object : AuthCallback {
                    override fun onLoginSuccess() {
                        criaPopup.setTitle("Transferência")
                        criaPopup.setMessage("Transferência realizada com sucesso!")
                        criaPopup.show()
                        transferirbotao.isEnabled = true;
                    }

                    override fun onLoginFailure() {
                        criaPopup.setTitle("Transferência")
                        criaPopup.setMessage("A transferência falhou, tente novamente!")
                        criaPopup.show()
                        transferirbotao.isEnabled = true;
                    }
                })
            }
        }
        return rootView
    }
}