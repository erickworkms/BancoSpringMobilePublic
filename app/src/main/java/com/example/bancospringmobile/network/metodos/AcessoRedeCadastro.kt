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
import com.example.bancospringmobile.network.DadosCadastro
import retrofit2.converter.gson.GsonConverterFactory


class AcessoRedeCadastro {
    suspend fun fazerRequisicao(dadosCadastro: DadosCadastro,context: Context,callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.cadastro(dadosCadastro).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val cadastroResponse = response.body()
                        // Faça algo com a resposta
                        Log.d("cadastro", "o cadastro funcionou "+cadastroResponse.toString())
                        callback.onLoginSuccess()
                    } else {
                        // Lidar com erros na resposta, como credenciais inválidas
                        Log.d("cadastro", "o cadastro falhou")
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