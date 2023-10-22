package com.example.bancospringmobile.network.metodos

import android.util.Log
import com.example.bancospringmobile.ui.telalogin.ServicoApi
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosListaConta
import retrofit2.converter.gson.GsonConverterFactory


class AcessoRedeCriaConta {
    suspend fun fazerRequisicao(tipoConta: String, usuario: String, context: Context, callback: AuthCallback) {
        return withContext(Dispatchers.IO) {


            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.criarConta(tipoConta, usuario).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        // Faça algo com a resposta
                        Log.d("Login", "o login funcionou " + loginResponse.toString())
                        callback.onLoginSuccess()
                    } else {
                        // Lidar com erros na resposta, como credenciais inválidas
                        Log.d("Login", "o login falso")
                        callback.onLoginFailure()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Lidar com falhas na chamada de rede
                    Log.d("Login", "o login falhou" + call.isExecuted + t.message)
                    callback.onLoginFailure()
                }
            }
            )
        }
    }

    suspend fun atualizarContas(usuario: String, tabela : TableLayout, context: Context, callback: AuthCallback) {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.retornarConta(usuario).enqueue(object : Callback<List<DadosListaConta>> {
                override fun onResponse(call: Call<List<DadosListaConta>>, response: Response<List<DadosListaConta>>) {
                    if (response.isSuccessful) {
                        val listaContas = response.body()

                        val count = tabela.childCount

                        for (i in 0 until count) {
                            val child = tabela.getChildAt(0)
                            if (child is TableRow) {
                                tabela.removeView(child)
                            }
                        }

                        if (listaContas != null) {
                            val listaDeRadio = mutableListOf<RadioButton>()
                            val radioButtonToTextViewMap = mutableMapOf<RadioButton, TextView>()

                            val tituloRow = TableRow(context)

                            val textViewAgencia = TextView(context)
                            textViewAgencia.text = "Agência"
                            val textViewNumero = TextView(context)
                            textViewNumero.text = "Número"
                            textViewNumero.gravity = Gravity.CENTER_HORIZONTAL
                            val textViewTipo = TextView(context)
                            textViewTipo.text = "Tipo de Conta"
                            val textViewSaldo = TextView(context)
                            textViewSaldo.text = "Saldo"

                            tituloRow.addView(TextView(context)) // Coluna em branco no início
                            tituloRow.addView(textViewAgencia)
                            tituloRow.addView(textViewNumero)
                            tituloRow.addView(textViewTipo)
                            tituloRow.addView(textViewSaldo)

                            tabela.addView(tituloRow)

                            for (conta in listaContas) {

                                val tableRow = TableRow(context)

                                val botaoOpcao = RadioButton(context)
                                botaoOpcao.text =""
                                botaoOpcao.id = View.generateViewId()
                                tableRow.id = botaoOpcao.id

                                val textViewAgencia = TextView(context)
                                textViewAgencia.text = conta.agencia
                                textViewAgencia.id = botaoOpcao.id

                                val textViewNumero = TextView(context)
                                textViewNumero.text = conta.numero
                                textViewAgencia.id = botaoOpcao.id

                                val textViewTipo = TextView(context)
                                textViewTipo.text = conta.tipoConta
                                textViewAgencia.id = botaoOpcao.id

                                val textViewSaldo = TextView(context)
                                textViewSaldo.text = conta.saldo
                                textViewAgencia.id = botaoOpcao.id

                                radioButtonToTextViewMap[botaoOpcao] = textViewNumero

                                listaDeRadio.add(botaoOpcao)

                                botaoOpcao.setOnClickListener {
                                    if (botaoOpcao.isChecked) {
                                        // Desmarque outros RadioButtons
                                        for (outroBotao in listaDeRadio) {
                                            if (outroBotao != botaoOpcao) {
                                                outroBotao.isChecked = false
                                            }
                                            else if(outroBotao == botaoOpcao){
                                                val textViewCorrespondente = radioButtonToTextViewMap[botaoOpcao]
                                                if (textViewCorrespondente != null) {
                                                    val texto = textViewCorrespondente.text.toString().replace("\"", "")
                                                    Log.d("Usuario", "$texto contas encontradas")
                                                    atualizaContaEscolhida(texto, usuario,context, callback)
                                                }
                                            }
                                        }
                                    }
                                }
                                tableRow.addView(botaoOpcao)
                                tableRow.addView(textViewAgencia)
                                tableRow.addView(textViewNumero)
                                tableRow.addView(textViewTipo)
                                tableRow.addView(textViewSaldo)

                                tabela.addView(tableRow)
                                Log.d("Usuario", " contas encontradas")
                            }
                        }
                        Log.d("Usuario", " contas encontradas")
                        callback.onLoginSuccess()
                    } else {
                        // Lidar com erros na resposta, como credenciais inválidas
                        Log.d("Usuario", "contas nao encontradas")
                        callback.onLoginFailure()
                    }
                }
                override fun onFailure(call: Call<List<DadosListaConta>>, t: Throwable) {
                    // Lidar com falhas na chamada de rede
                    Log.d("Usuario", "contas nao encontradas")
                    callback.onLoginFailure()
                }
            }
            )
        }
    }

    private fun atualizaContaEscolhida(
        texto: String,
        usuario: String,
        context: Context,
        callback: AuthCallback
    ) {
        val apiService = BaseConversorHttp().servicoApi(context)

        apiService.atualizarContaEscolhida(texto, usuario).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val atualizaContaResponse = response.body()
                    // Faça algo com a resposta
                    Log.d("cadastro", "conta foi escolhida " + atualizaContaResponse.toString())
                    callback.onLoginSuccess()
                } else {
                    // Lidar com erros na resposta, como credenciais inválidas
                    Log.d("cadastro", "a conta nao atualizou")
                    callback.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Lidar com falhas na chamada de rede
                Log.d("cadastro", "a conta nao atualizou")
                callback.onLoginFailure()
            }
        }
        )
    }

}