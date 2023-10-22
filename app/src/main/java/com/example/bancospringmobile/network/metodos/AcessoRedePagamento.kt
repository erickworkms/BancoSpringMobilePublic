package com.example.bancospringmobile.network.metodos

import android.content.Context
import android.util.Log
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosPagamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AcessoRedePagamento {
    suspend fun fazerRequisicao(usuario: String,dadosPagamento: DadosPagamento,context: Context,callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.realizarPagamento(dadosPagamento,usuario).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val pagamentoResponse = response.body()

                        Log.d("Pagamento", "O pagamento foi realizado "+pagamentoResponse.toString())
                        callback.onLoginSuccess()
                    } else {

                        Log.d("Pagamento", "Ocorreu um erro no pagamento")
                        callback.onLoginFailure()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    Log.d("Pagamento", "Ocorreu um erro no pagamento")
                    callback.onLoginFailure()
                }
            }
            )
        }
    }
}