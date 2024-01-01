package com.fabiokusaba.aulawhatsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fabiokusaba.aulawhatsapp.databinding.ItemMensagensDestinatarioBinding
import com.fabiokusaba.aulawhatsapp.databinding.ItemMensagensRemetenteBinding
import com.fabiokusaba.aulawhatsapp.model.Mensagem
import com.fabiokusaba.aulawhatsapp.utils.Constantes
import com.google.firebase.auth.FirebaseAuth

class ConversasAdapter : Adapter<ViewHolder>() {
    private var listaMensagens = emptyList<Mensagem>()

    fun adicionarLista(lista: List<Mensagem>) {
        listaMensagens = lista
        notifyDataSetChanged()
    }

    class MensagensRemetenteViewHolder(
        private val binding: ItemMensagensRemetenteBinding
    ) : ViewHolder(binding.root) {
        fun bind(mensagem: Mensagem) {
            binding.textMensagemRemetente.text = mensagem.mensagem
        }

        companion object {
            fun inflarLayout(parent: ViewGroup) : MensagensRemetenteViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = ItemMensagensRemetenteBinding.inflate(
                    inflater, parent, false
                )
                return MensagensRemetenteViewHolder(itemView)
            }
        }
    }

    class MensagensDestinatarioViewHolder(
        private val binding: ItemMensagensDestinatarioBinding
    ) : ViewHolder(binding.root) {
        fun bind(mensagem: Mensagem) {
            binding.textMensagemDestinatario.text = mensagem.mensagem
        }

        companion object {
            fun inflarLayout(parent: ViewGroup) : MensagensDestinatarioViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = ItemMensagensDestinatarioBinding.inflate(
                    inflater, parent, false
                )
                return MensagensDestinatarioViewHolder(itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val mensagem = listaMensagens[position]
        val idUsuarioLogado = FirebaseAuth.getInstance().currentUser?.uid.toString()

        return if (idUsuarioLogado == mensagem.idUsuario) {
            Constantes.TIPO_REMETENTE
        } else {
            Constantes.TIPO_DESTINATARIO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == Constantes.TIPO_REMETENTE) {
            MensagensRemetenteViewHolder.inflarLayout(parent)
        } else {
            MensagensDestinatarioViewHolder.inflarLayout(parent)
        }
    }

    override fun getItemCount(): Int {
        return listaMensagens.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mensagem = listaMensagens[position]

        when(holder) {
            is MensagensRemetenteViewHolder -> holder.bind(mensagem)
            is MensagensDestinatarioViewHolder -> holder.bind(mensagem)
        }
    }
}