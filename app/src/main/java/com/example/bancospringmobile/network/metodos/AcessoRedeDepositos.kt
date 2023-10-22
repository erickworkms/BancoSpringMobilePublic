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
import com.example.bancospringmobile.R
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosDeposito
import com.example.bancospringmobile.network.DadosTransferencia
import retrofit2.converter.gson.GsonConverterFactory


class AcessoRedeDepositos {
    suspend fun fazerRequisicao(usuario: String, dadosDeposito: DadosDeposito, context: Context, callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.realizarDeposito(dadosDeposito,usuario).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val depositoResponse = response.body()
                        // Faça algo com a resposta
                        Log.d("Deposito", "o login funcionou "+depositoResponse.toString())
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
}