package com.fabiokusaba.aulawhatsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fabiokusaba.aulawhatsapp.databinding.FragmentConversasBinding
import com.fabiokusaba.aulawhatsapp.model.Conversa
import com.fabiokusaba.aulawhatsapp.utils.Constantes
import com.fabiokusaba.aulawhatsapp.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ConversasFragment : Fragment() {
    private lateinit var binding: FragmentConversasBinding
    private lateinit var eventoSnapshot: ListenerRegistration

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConversasBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adicionarListenerConversas()
    }

    override fun onDestroy() {
        super.onDestroy()
        eventoSnapshot.remove()
    }

    private fun adicionarListenerConversas() {
        val idUsuarioRemetente = firebaseAuth.currentUser?.uid

        if (idUsuarioRemetente != null) {
            eventoSnapshot = firestore
                .collection(Constantes.CONVERSAS)
                .document(idUsuarioRemetente)
                .collection(Constantes.ULTIMAS_CONVERSAS)
                .addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        activity?.exibirMensagem("Erro ao recuperar conversas")
                    }

                    val listaConversas = mutableListOf<Conversa>()
                    val documentos = querySnapshot?.documents

                    documentos?.forEach { documentSnapshot ->
                        val conversa = documentSnapshot.toObject(Conversa::class.java)

                        if (conversa != null) {
                            listaConversas.add(conversa)
                        }
                    }

                    if (listaConversas.isNotEmpty()) {
                        //Atualizar o adapter
                    }
                }
        }
    }
}