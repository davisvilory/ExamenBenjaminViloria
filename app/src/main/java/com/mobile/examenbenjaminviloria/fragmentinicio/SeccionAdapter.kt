package com.mobile.examenbenjaminviloria.fragmentinicio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.examenbenjaminviloria.databinding.AdapterSeccionesBinding
import com.mobile.examenbenjaminviloria.utils.Categorias

class SeccionAdapter(private val items: MutableList<Categorias>,
                     private val clickFunction: (Categorias) -> Unit
) : RecyclerView.Adapter<SeccionAdapter.VHThis>() {

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int): VHThis {
        val binding =
            AdapterSeccionesBinding.inflate(LayoutInflater.from(p.context), p, false)
        return VHThis(binding)
    }

    override fun onBindViewHolder(holder: VHThis, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class VHThis(val binding: AdapterSeccionesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Categorias, position: Int) {
            with(binding) {
                item.nombre?.let { seccionTitulo.text = it }
                seccionContenedor.setOnClickListener {
                    clickFunction(item)
                }
            }
        }
    }
}