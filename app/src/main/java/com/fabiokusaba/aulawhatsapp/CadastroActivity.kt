package com.fabiokusaba.aulawhatsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fabiokusaba.aulawhatsapp.databinding.ActivityCadastroBinding
import com.fabiokusaba.aulawhatsapp.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var senha: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()
        inicializarEventosClique()
    }

    private fun inicializarEventosClique() {
        binding.btnCadastrar.setOnClickListener {
            if (valirdarCampos()) {
                cadastrarUsuario(nome, email, senha)
            }
        }
    }

    private fun cadastrarUsuario(nome: String, email: String, senha: String) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, senha
        ).addOnCompleteListener { resultado ->
            if (resultado.isSuccessful) {
                exibirMensagem("Sucesso ao fazer o seu cadastro")

                startActivity(
                    Intent(applicationContext, MainActivity::class.java)
                )
            }
        }.addOnFailureListener { erro ->
            try {
                throw erro
            } catch (erroSenhaFraca: FirebaseAuthWeakPasswordException) {
                erroSenhaFraca.printStackTrace()
                exibirMensagem(
                    "Senha fraca, digite outra com letras, números e caracteres especiais"
                )
            } catch (erroUsuarioExistente: FirebaseAuthUserCollisionException) {
                erroUsuarioExistente.printStackTrace()
                exibirMensagem("E-mail já cadastrado")
            } catch (erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException) {
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("E-mail inválido, digite um outro e-mail")
            }
        }
    }

    private fun valirdarCampos(): Boolean {
        nome = binding.editNome.text.toString()
        email = binding.editEmail.text.toString()
        senha = binding.editSenha.text.toString()

        if (nome.isNotEmpty()) {
            binding.textInputLayoutNome.error = null

            if (email.isNotEmpty()) {
                binding.textInputLayoutEmail.error = null

                if (senha.isNotEmpty()) {
                    binding.textInputLayoutSenha.error = null
                    return true
                } else {
                    binding.textInputLayoutSenha.error = "Preencha a sua senha!"
                    return false
                }
            } else {
                binding.textInputLayoutEmail.error = "Preencha o seu e-mail!"
                return false
            }

        } else {
            binding.textInputLayoutNome.error = "Preencha o seu nome!"
            return false
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Faça o seu cadastro"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}