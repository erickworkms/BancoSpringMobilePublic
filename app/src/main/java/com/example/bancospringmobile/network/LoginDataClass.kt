package com.example.bancospringmobile.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class DadosLogin(val usuario: String, val senha: String)
data class DadosResposta(val token: String)

data class DadosCadastro(
    val nome: String,
    val dataNascimento: String,
    val cpf: String,
    val endereco: String,
    val telefone: String,
    val email: String,
    val usuario: String,
    val senha: String
)
data class DadosContaLogin(
    val verUsuarioLogado: Boolean,
    val usuario: String,
    val numero: Int,
    val agencia: String,
    val saldo: Double,
    val idCliente: UUID,
    val tipoConta: String,
)

data class DadosListaConta(
    val agencia: String,
    val numero: String,
    val tipoConta: String,
    val saldo: String
)

data class DadosPagamento(
    val dataPagamento: String,
    val codigoBarra: String,
    val valor: String,
    val dataVencimento: String
)

data class DadosExtratoData(
    val dataInicial: String,
    val dataFinal: String
)
data class DadosExtratoDepositos(
    val dataDeposito: String,
    val numeroConta: String,
    val agencia: String,
    val tipoConta: String,
    val depositante: String,
    val recebedor: String,
    val valor: String
)
data class DadosExtratoTransferencias(
    val destinatario: String,
    val recebedor: String,
    val data: String,
    val valor: String
)
data class DadosExtratoPagamentos(
    val dataPagamento: String,
    val codigoBarra: String,
    val valor: String,
    val dataVencimento: String
)
data class DadosDeposito(
    val dataDeposito: String,
    val numeroConta: String,
    val agencia: String,
    val tipoConta: String,
    val cpf: String,
    val valor: String
)
data class DadosEmprestimo(
    val dataPagamento: String,
    val valor: String
)

data class DadosTransferencia(
    val dataPagamento: String,
    val numero: String,
    val agencia: String,
    val tipoConta: String,
    val cpf: String,
    val valor: String
)