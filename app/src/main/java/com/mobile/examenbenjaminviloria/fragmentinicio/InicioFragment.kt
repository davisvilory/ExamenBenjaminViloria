package com.mobile.examenbenjaminviloria.fragmentinicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.examenbenjaminviloria.databinding.FragmentInicioBinding
import com.mobile.examenbenjaminviloria.utils.*

class InicioFragment : BaseFragment() {
    private lateinit var thisViewModel: InicioViewModel
    private val binding: FragmentInicioBinding by lazy {
        FragmentInicioBinding.inflate(LayoutInflater.from(context), null, false)
    }
    private val thisTag = "Log_InicioFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisViewModel = ViewModelProvider(requireActivity())[InicioViewModel::class.java]
        try {
            showProgress(true)
            setObservables()
            initUI()
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
    }

    private fun initUI() {
        try {
            with(binding) {
                val categorias = mutableListOf<Categorias>()
                categorias.add(Categorias("Trending", 1))
                categorias.add(Categorias("Popular", 2))
                rvSecciones.apply {
                    adapter = SeccionAdapter(categorias) {
                        run {
                            //limpiado del contenido para mejor scroll y sensacion de limpieza
                            rvSourceScreens.apply {
                                adapter = MovieAdapter(mutableListOf())
                                layoutManager = LinearLayoutManager(context)
                                isNestedScrollingEnabled = true
                            }
                            it.id?.let { id -> thisViewModel.getMovies(id) }
                        }
                    }
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    isNestedScrollingEnabled = true
                }
            }

            activity?.onBackPressedDispatcher?.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        //se evita salir de la app
                    }
                })
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
    }

    private fun setObservables() {
        thisViewModel.thisStatus.observe(viewLifecycleOwner) {
            when (it) {
                is AsyncStatus.Init -> {}
                is AsyncStatus.Success -> fillReciclers(it.type)
                is AsyncStatus.Failure -> Global.logError(thisTag, it.message)
                else -> {}
            }
        }
        thisViewModel.isLoading.observe(viewLifecycleOwner) { ll -> showProgress(ll) }
    }

    private fun fillReciclers(type: Int) {
        try {
            when (type) {
                1 -> {
                    binding.rvSourceScreens.apply {
                        adapter = MovieAdapter(thisViewModel.db.results)
                        layoutManager = LinearLayoutManager(context)
                        isNestedScrollingEnabled = true
                    }
                    showProgress(false)
                }
                else -> {}
            }
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
    }

    override fun onDestroy() {
        try {
            if (this::thisViewModel.isInitialized)
                thisViewModel.db = MoviesDB()
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
        super.onDestroy()
    }
}