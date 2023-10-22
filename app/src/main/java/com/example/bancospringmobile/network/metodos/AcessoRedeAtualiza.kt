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
import com.example.bancospringmobile.network.DadosContaLogin
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class AcessoRedeAtualiza {

    var resposta : DadosCadastro = DadosCadastro("","","","","", "","","")
    suspend fun atualizaCadastro(usuario: String, dadosCadastro: DadosCadastro,context: Context,callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val apiService = retrofit.create(ServicoApi::class.java)

            apiService.atualizarCadastro(dadosCadastro,usuario).enqueue(object : Callback<String> {
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

    suspend fun recebeCadastro(usuario: String,context: Context,callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.15.180:8080") // Substitua pela URL da sua API
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val apiService = retrofit.create(ServicoApi::class.java)

            apiService.verDadosCadastro(usuario).enqueue(object : Callback<DadosCadastro> {
                override fun onResponse(call: Call<DadosCadastro>, response: Response<DadosCadastro>) {
                    if (response.isSuccessful) {
                        val cadastroResponse = response.body()
                        if (cadastroResponse != null) {
                            resposta = cadastroResponse
                        }
                        // Faça algo com a resposta
                        Log.d("cadastro", "o cadastro funcionou "+cadastroResponse.toString())
                        callback.onLoginSuccess()
                    } else {
                        // Lidar com erros na resposta, como credenciais inválidas
                        Log.d("cadastro", "o cadastro falhou")
                        callback.onLoginFailure()
                    }
                }

                override fun onFailure(call: Call<DadosCadastro>, t: Throwable) {
                    // Lidar com falhas na chamada de rede
                    Log.d("Login", "o login falhou" + call.isExecuted + t.message)
                    callback.onLoginFailure()
                }
            }
            )
        }
    }

}