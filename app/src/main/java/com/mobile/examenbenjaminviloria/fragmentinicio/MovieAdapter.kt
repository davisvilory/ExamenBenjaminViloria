package com.mobile.examenbenjaminviloria.fragmentinicio

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobile.examenbenjaminviloria.databinding.AdapterMovieBinding
import com.mobile.examenbenjaminviloria.utils.Global
import com.mobile.examenbenjaminviloria.utils.Results
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val items: MutableList<Results>
) : RecyclerView.Adapter<MovieAdapter.VHThis>() {

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int): VHThis {
        val binding =
            AdapterMovieBinding.inflate(LayoutInflater.from(p.context), p, false)
        return VHThis(binding)
    }

    override fun onBindViewHolder(holder: VHThis, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class VHThis(val binding: AdapterMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Results, position: Int) {
            with(binding) {
                item.originalTitle?.let { Titulo.text = it }
                item.originalName?.let { Titulo.text = it }
                item.releaseDate?.let { Fecha.text = it }
                item.firstAirDate?.let { Fecha.text = it }
                content.setOnClickListener {
                    Toast.makeText(
                        root.context,
                        "Se dio click en la pelicula ${item.title}",
                        Toast.LENGTH_LONG
                    ).show()
                    Global.events.clear()
                    Global.events[item.title!!] = item.id!!.toString()
                    Global.trackEvent("Se dio click en la pelicula ${item.title}")
                }
                item.posterPath.let {
                    val uri: Uri = Uri.parse("https://image.tmdb.org/t/p/w220_and_h330_face${it}")
                    Picasso.get().load(uri).into(Image)
                }
            }
        }
    }
}