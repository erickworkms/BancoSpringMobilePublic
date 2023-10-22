package com.example.bancospringmobile.ui.telalogin
import com.example.bancospringmobile.network.*

import retrofit2.Call
import retrofit2.http.*

interface ServicoApi {
    @FormUrlEncoded
    @POST("/login/verlogin")
    fun login(
        @Field("nome") nome: String,
        @Field("senha") senha: String
    ): Call<String>

    @POST("/login/crialogin")
    fun cadastro(
        @Body dados: DadosCadastro
    ): Call<String>

    @GET("/login/verDadoslogin")
    fun sessao(
        @Header("Authorization") usuario : String
    ): Call<DadosContaLogin>

    @POST("/contas/criarConta")
    fun criarConta(
        @Body contaEscolhida: String,
        @Header("Authorization") usuario : String
    ): Call<String>

    @GET("/contas/retornarcontas")
    fun retornarConta(
        @Header("Authorization") usuario : String
    ): Call<List<DadosListaConta>>

    @POST("/login/atualizaContaEscolhida")
    fun atualizarContaEscolhida(
        @Body contaEscolhida: String,
        @Header("Authorization") usuario : String
    ): Call<String>

    @POST("/login/transferencia")
    fun realizarTransferencia(
        @Body dadosTransferencia: DadosTransferencia,
        @Header("Authorization") usuario : String
    ): Call<String>

    @POST("/login/pagamento")
    fun realizarPagamento(
        @Body dadosPagamento: DadosPagamento,
        @Header("Authorization") usuario : String
    ): Call<String>

    @POST("/login/deposito")
    fun realizarDeposito(
        @Body dadosDeposito: DadosDeposito,
        @Header("Authorization") usuario : String
    ): Call<String>

    @POST("/login/emprestimo")
    fun realizarEmprestimo(
        @Body emprestimo: DadosEmprestimo,
        @Header("Authorization") usuario : String
    ): Call<String>

    @POST("/login/atualizarCadastro")
    fun atualizarCadastro(
        @Body cadastro: DadosCadastro,
        @Header("Authorization") usuario : String
    ): Call<String>

    @GET("/login/verDadosCadastro")
    fun verDadosCadastro(
        @Header("Authorization") usuario : String
    ): Call<DadosCadastro>

    @POST("/login/extratoDeposito")
    fun verListaDepositos(
        @Body data: DadosExtratoData,
        @Header("Authorization") usuario : String
    ): Call<List<DadosExtratoDepositos>>

    @POST("/login/extratoPagamento")
    fun verListaPagamentos(
        @Body data: DadosExtratoData,
        @Header("Authorization") usuario : String
    ): Call<List<DadosExtratoPagamentos>>

    @POST("/login/extratoTransferencia")
    fun verListaTransferencias(
        @Body data: DadosExtratoData,
        @Header("Authorization") usuario : String
    ): Call<List<DadosExtratoTransferencias>>
}