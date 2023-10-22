package com.example.bancospringmobile.network.metodos

import android.content.Context
import android.util.Log
import com.example.bancospringmobile.network.AuthCallback
import com.example.bancospringmobile.network.DadosContaLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AcessoRedeSessao {

    var resposta : DadosContaLogin = DadosContaLogin(false,"",0,"0",0.0, UUID.randomUUID(),"0")
    suspend fun fazerRequisicao(token:String,context: Context,callback: AuthCallback) {
        return withContext(Dispatchers.IO) {

            val apiService = BaseConversorHttp().servicoApi(context)

           apiService.sessao(token).enqueue(object : Callback<DadosContaLogin> {
                override fun onResponse(call: Call<DadosContaLogin>, response: Response<DadosContaLogin>) {
                    if (response.isSuccessful) {
                        val sessaoResponse = response.body()

                        if (sessaoResponse != null) {
                            resposta = sessaoResponse
                        }

                        Log.d("Login", "o login funcionou "+sessaoResponse.toString())
                        callback.onLoginSuccess()
                    } else {

                        Log.d("Login", "o login falso")
                        callback.onLoginFailure()
                    }
                }

                override fun onFailure(call: Call<DadosContaLogin>, t: Throwable) {

                    Log.d("Login", "o login falhou" + call.isExecuted + t.message)
                    callback.onLoginFailure()
                }
            }
            )
        }
    }
}