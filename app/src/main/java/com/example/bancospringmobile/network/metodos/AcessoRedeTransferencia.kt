package com.example.bancospringmobile.network.metodos

import android.util.Log
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.content.Context

import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosTransferencia


class AcessoRedeTransferencia : BaseConversorHttp() {
    suspend fun fazerRequisicao(
        usuario: String,
        dadosTransferencia: DadosTransferencia,
        context: Context,
        callback: AuthCallback
    ) {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.realizarTransferencia(dadosTransferencia, usuario).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val transferenciaResponse = response.body()


                        Log.d("Transferencia", "a transferencia funcionou " + transferenciaResponse.toString())
                        callback.onLoginSuccess()
                    } else {

                        Log.d("Transferencia", "Erro ao realizar a transferencia")
                        callback.onLoginFailure()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    Log.d("Transferencia", "Erro ao realizar a transferencia")
                    callback.onLoginFailure()
                }
            }
            )
        }
    }
}