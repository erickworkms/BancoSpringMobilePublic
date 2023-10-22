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


class AcessoRedeLogin {
    suspend fun fazerRequisicao(usuario: String, senha: String,context: Context,callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val preferencias = context.getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE)

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.login(usuario, senha).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        val token = loginResponse.toString()
                        preferencias.edit().putString("cache-login", token).apply()
                        // Faça algo com a resposta
                        Log.d("Login", "o login funcionou "+loginResponse.toString())
                        callback.onLoginSuccess()

                    } else {
                        // Lidar com erros na resposta, como credenciais inválidas
                        Log.d("Login", "falha no login")
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