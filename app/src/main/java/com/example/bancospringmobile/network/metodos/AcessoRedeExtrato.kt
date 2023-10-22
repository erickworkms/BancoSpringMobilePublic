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
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AcessoRedeExtrato {

    var respostaDeposito : List<DadosExtratoDepositos> = ArrayList()
    var respostaPagamento : List<DadosExtratoPagamentos> = ArrayList()
    var respostaTransferencia : List<DadosExtratoTransferencias> = ArrayList()
    suspend fun buscar(usuario: String, extratoData: DadosExtratoData, tabela : TableLayout, context: Context, callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val apiService = retrofit.create(ServicoApi::class.java)

            buscarDepositos(apiService, extratoData, usuario, callback,context,tabela)
            buscarPagamentos(apiService, extratoData, usuario, callback,context,tabela)
            buscarTransferencias(apiService, extratoData, usuario, callback,context,tabela)
        }
    }

    private fun geraTabelaDepositos(context: Context, tabela: TableLayout) {

        if (respostaDeposito != null) {
            val tituloRow = TableRow(context)
            val colunasRow = TableRow(context)
            val textViewDeposito = TextView(context)
            textViewDeposito.text = "Depositos"

            val textViewDataDeposito = TextView(context)
            textViewDataDeposito.text = "Agência"
            val textViewNumero = TextView(context)
            textViewNumero.text = "Número"
            val textViewAgencia = TextView(context)
            textViewAgencia.text = "Agência"
            val textViewTipo = TextView(context)
            textViewTipo.text = "Tipo de Conta"
            val textViewDepositante = TextView(context)
            textViewDepositante.text = "Depositante"
            val textViewRecebedor = TextView(context)
            textViewRecebedor.text = "Recebedor"
            val textViewValor = TextView(context)
            textViewValor.text = "Valor"

            colunasRow.addView(TextView(context))

            tituloRow.addView(textViewDataDeposito)
            tituloRow.addView(textViewNumero)
            tituloRow.addView(textViewAgencia)
            tituloRow.addView(textViewTipo)
            tituloRow.addView(textViewDepositante)
            tituloRow.addView(textViewRecebedor)
            tituloRow.addView(textViewValor)

            tabela.addView(TextView(context))
            tabela.addView(textViewDeposito)
            tabela.addView(TextView(context))
            tabela.addView(tituloRow)
            tabela.addView(colunasRow)
            for (depositos in respostaDeposito) {

                val tableRow = TableRow(context)

                val textViewDataDepositoValor = TextView(context)
                textViewDataDepositoValor.text = depositos.dataDeposito
                val textViewNumeroValor = TextView(context)
                textViewNumeroValor.text = depositos.numeroConta
                val textViewAgenciaValor = TextView(context)
                textViewAgenciaValor.text = depositos.agencia
                val textViewTipoValor = TextView(context)
                textViewTipoValor.text = depositos.tipoConta
                val textViewDepositanteValor = TextView(context)
                textViewDepositanteValor.text = depositos.depositante
                val textViewRecebedorValor = TextView(context)
                textViewRecebedorValor.text = depositos.recebedor
                val textViewValorResposta = TextView(context)
                textViewValorResposta.text = depositos.valor

                tableRow.addView(textViewDataDepositoValor)
                tableRow.addView(textViewNumeroValor)
                tableRow.addView(textViewAgenciaValor)
                tableRow.addView(textViewTipoValor)
                tableRow.addView(textViewDepositanteValor)
                tableRow.addView(textViewRecebedorValor)
                tableRow.addView(textViewValorResposta)

                tabela.addView(tableRow)
            }
        }
    }

    private fun geraTabelaTransferencias(context: Context, tabela: TableLayout) {
        if (respostaTransferencia != null) {
            val tituloRow = TableRow(context)
            val colunasRow = TableRow(context)
            val textViewDeposito = TextView(context)
            textViewDeposito.text = "Transferencias"

            val textViewDepositante = TextView(context)
            textViewDepositante.text = "Destinatário"
            val textViewRecebedor = TextView(context)
            textViewRecebedor.text = "Recebedor"
            val textViewDataDeposito = TextView(context)
            textViewDataDeposito.text = "Data"
            val textViewValor = TextView(context)
            textViewValor.text = "Valor"


            colunasRow.addView(TextView(context))

            tituloRow.addView(textViewDataDeposito)
            tituloRow.addView(textViewDepositante)
            tituloRow.addView(textViewRecebedor)
            tituloRow.addView(textViewValor)

            tabela.addView(TextView(context))
            tabela.addView(textViewDeposito)
            tabela.addView(TextView(context))
            tabela.addView(tituloRow)
            tabela.addView(colunasRow)
            for (transferencias in respostaTransferencia) {

                val tableRow = TableRow(context)

                val textViewDepositanteValor = TextView(context)
                textViewDepositanteValor.text = transferencias.destinatario
                val textViewRecebedorValor = TextView(context)
                textViewRecebedorValor.text = transferencias.recebedor
                val textViewDataDepositoValor = TextView(context)
                textViewDataDepositoValor.text = transferencias.data
                val textViewValorResposta = TextView(context)
                textViewValorResposta.text = transferencias.valor

                tableRow.addView(textViewDepositanteValor)
                tableRow.addView(textViewRecebedorValor)
                tableRow.addView(textViewDataDepositoValor)
                tableRow.addView(textViewValorResposta)

                tabela.addView(tableRow)
            }
        }
    }

    private fun geraTabelaPagamentos(context: Context, tabela: TableLayout) {
        if (respostaPagamento != null) {
            val tituloRow = TableRow(context)
            val colunasRow = TableRow(context)
            val textViewDeposito = TextView(context)
            textViewDeposito.text = "Pagamentos"

            val textViewdataPagamento = TextView(context)
            textViewdataPagamento.text = "DataPagamento"
            val textViewCodigoBarra = TextView(context)
            textViewCodigoBarra.text = "Codigo Barra"
            val textViewValor = TextView(context)
            textViewValor.text = "Valor"
            val textViewDataVencimento = TextView(context)
            textViewDataVencimento.text = "DataVencimento"


            colunasRow.addView(TextView(context))

            tituloRow.addView(textViewdataPagamento)
            tituloRow.addView(textViewCodigoBarra)
            tituloRow.addView(textViewValor)
            tituloRow.addView(textViewDataVencimento)

            tabela.addView(TextView(context))
            tabela.addView(textViewDeposito)
            tabela.addView(TextView(context))
            tabela.addView(tituloRow)
            tabela.addView(colunasRow)

            for (pagamento in respostaPagamento) {

                val tableRow = TableRow(context)

                val textViewDataPagamentoValor = TextView(context)
                textViewDataPagamentoValor.text = pagamento.dataPagamento
                val textViewCodigoBarraValor = TextView(context)
                textViewCodigoBarraValor.text = pagamento.codigoBarra
                val textViewValorResposta = TextView(context)
                textViewValorResposta.text = pagamento.valor
                val textViewDataVencimentoValor = TextView(context)
                textViewDataVencimentoValor.text = pagamento.dataVencimento


                tableRow.addView(textViewDataPagamentoValor)
                tableRow.addView(textViewCodigoBarraValor)
                tableRow.addView(textViewValorResposta)
                tableRow.addView(textViewDataVencimentoValor)

                tabela.addView(tableRow)
            }
        }
    }

    private fun buscarDepositos(
        apiService: ServicoApi,
        extratoData: DadosExtratoData,
        usuario: String,
        callback: AuthCallback,
        context: Context,
        tabela: TableLayout
    ) {
        apiService.verListaDepositos(extratoData, usuario).enqueue(object : Callback<List<DadosExtratoDepositos>> {
            override fun onResponse(call: Call<List<DadosExtratoDepositos>>, response: Response<List<DadosExtratoDepositos>>) {
                if (response.isSuccessful) {
                    val depositoResponse = response.body()
                    if (depositoResponse != null) {
                        respostaDeposito = depositoResponse
                    }
                    // Faça algo com a resposta
                    Log.d("Deposito", "Dados do deposito foram encontrados " + depositoResponse.toString())
                    geraTabelaDepositos(context, tabela)
                    callback.onLoginSuccess()
                } else {
                    // Lidar com erros na resposta, como credenciais inválidas
                    Log.d("Deposito", "Erro ao encontrar os dados")
                    callback.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<List<DadosExtratoDepositos>>, t: Throwable) {
                // Lidar com falhas na chamada de rede
                Log.d("Deposito", "Erro ao encontrar os dados")
                callback.onLoginFailure()
            }
        }
        )
    }

    private fun buscarPagamentos(
        apiService: ServicoApi,
        extratoData: DadosExtratoData,
        usuario: String,
        callback: AuthCallback,
        context: Context,
        tabela: TableLayout
    ) {
        apiService.verListaPagamentos(extratoData, usuario).enqueue(object : Callback<List<DadosExtratoPagamentos>> {
            override fun onResponse(call: Call<List<DadosExtratoPagamentos>>, response: Response<List<DadosExtratoPagamentos>>) {
                if (response.isSuccessful) {
                    val pagamentosResponse = response.body()
                    if (pagamentosResponse != null) {
                        respostaPagamento = pagamentosResponse
                    }
                    // Faça algo com a resposta
                    Log.d("Pagamento", "Os dados de pagamento foram encontrados " + pagamentosResponse.toString())
                    geraTabelaPagamentos(context, tabela)
                    callback.onLoginSuccess()
                } else {
                    // Lidar com erros na resposta, como credenciais inválidas
                    Log.d("Pagamento", "Não foram encontrados os dados de pagamento")
                    callback.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<List<DadosExtratoPagamentos>>, t: Throwable) {
                // Lidar com falhas na chamada de rede
                Log.d("Pagamento", "Não foram encontrados os dados de pagamento")
                callback.onLoginFailure()
            }
        }
        )
    }

    private fun buscarTransferencias(
        apiService: ServicoApi,
        extratoData: DadosExtratoData,
        usuario: String,
        callback: AuthCallback,
        context: Context,
        tabela: TableLayout
    ) {
        apiService.verListaTransferencias(extratoData, usuario).enqueue(object : Callback<List<DadosExtratoTransferencias>> {
            override fun onResponse(call: Call<List<DadosExtratoTransferencias>>, response: Response<List<DadosExtratoTransferencias>>) {
                if (response.isSuccessful) {
                    val transferenciasResponse = response.body()
                    if (transferenciasResponse != null) {
                        respostaTransferencia=transferenciasResponse
                    }
                    // Faça algo com a resposta
                    Log.d("Transferencia", "Foram encontrados os dados de transferencias " + transferenciasResponse.toString())
                    geraTabelaTransferencias(context, tabela)
                    callback.onLoginSuccess()
                } else {
                    // Lidar com erros na resposta, como credenciais inválidas
                    Log.d("Transferencia", "Não foram encontrados dados das transferencias")
                    callback.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<List<DadosExtratoTransferencias>>, t: Throwable) {
                // Lidar com falhas na chamada de rede
                Log.d("Transferencia", "Não foram encontrados dados das transferencias")
                callback.onLoginFailure()
            }
        }
        )
    }
}