package com.mobile.examenbenjaminviloria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mobile.examenbenjaminviloria.databinding.ActivityMainBinding
import com.mobile.examenbenjaminviloria.utils.Global

class MainActivity : AppCompatActivity() {
    private val thisTag: String = "Log_MainActivity"
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        try {
            Global.initAnalytics()
            Global.originalFragment = R.id.inicioFragment

            val hostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = hostFragment.navController

            with(binding) {
                bottomNavigationView.setupWithNavController(navController)
                bottomNavigationView.setOnItemSelectedListener { item ->
                    showProgressFragment(true)
                    item.isChecked = true

                    when (item.itemId) {
                        R.id.menuInicio -> {
                            if (Global.originalFragment > 0) {
                                //se limpia y se navega de nuevo al principio
                                navController.popBackStack(Global.originalFragment, true)
                            }
                            Global.originalFragment = R.id.inicioFragment
                        }
                    }
                    navController.navigate(Global.originalFragment)
                    true
                }
            }
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
    }

    fun showProgressFragment(show: Boolean) {
        Global.showProgress(show, window, binding.customLoader.loader)
    }
}