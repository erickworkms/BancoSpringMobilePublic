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
import com.example.bancospringmobile.network.DadosEmprestimo
import retrofit2.converter.gson.GsonConverterFactory


class AcessoRedeEmprestimo {
    suspend fun fazerRequisicao(usuario: String, dadosEmprestimo: DadosEmprestimo,context: Context,callback: AuthCallback)  {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

            apiService.realizarEmprestimo(dadosEmprestimo,usuario).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val emprestimoResponse = response.body()

                        Log.d("Emprestimo", "O Emprestimo foi realizado com sucesso "+emprestimoResponse.toString())
                        callback.onLoginSuccess()
                    } else {

                        Log.d("Emprestimo", "Erro ao receber o Emprestimo")
                        callback.onLoginFailure()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Lidar com falhas na chamada de rede
                    Log.d("Emprestimo", "Erro ao receber o Emprestimo")
                    callback.onLoginFailure()
                }
            }
            )
        }
    }
}