package com.example.bancospringmobile.ui.extrato

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosExtratoData
import com.example.bancospringmobile.network.metodos.AcessoRedeExtrato
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TelaExtratoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.pagina_extrato, container, false)
        val voltarbotao = rootView.findViewById<Button>(R.id.voltar_botao)
        val pesquisabotao = rootView.findViewById<Button>(R.id.botao_pesquisar)
        val tabela = rootView.findViewById<TableLayout>(R.id.extrato_tabela)
        tabela.removeAllViews()

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
            findNavController().navigate(R.id.action_Tela_Extrato_to_Tela_Sessao)
        }

        pesquisabotao.setOnClickListener {
            pesquisabotao.isEnabled = false;
            val classe = AcessoRedeExtrato()
            val dadosExtrato = DadosExtratoData(
                rootView.findViewById<EditText>(R.id.campo_dataInicial).text.toString(),
                rootView.findViewById<EditText>(R.id.campo_dataFinal).text.toString()
            )
            GlobalScope.launch(Dispatchers.Main) {
                classe.buscar(
                    token.toString(),
                    dadosExtrato,
                    tabela,
                    requireContext(),
                    object : AuthCallback {
                        override fun onLoginSuccess() {
                            criaPopup.setTitle("Extrato")
                            criaPopup.setMessage("Extrato foi gerado com sucesso!")
                            criaPopup.show()
                            pesquisabotao.isEnabled = true;
                        }

                        override fun onLoginFailure() {
                            criaPopup.setTitle("Extrato")
                            criaPopup.setMessage("Dados não encontrados!")
                            criaPopup.show()
                            pesquisabotao.isEnabled = true;
                        }
                    }
                )

            }
        }
        return rootView
    }
}