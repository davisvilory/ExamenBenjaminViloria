package com.mobile.examenbenjaminviloria.fragmentinicio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mobile.examenbenjaminviloria.utils.AsyncStatus
import com.mobile.examenbenjaminviloria.utils.Global
import com.mobile.examenbenjaminviloria.utils.MoviesDB
import okhttp3.*
import okio.IOException

class InicioViewModel : ViewModel() {
    var db: MoviesDB = MoviesDB()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val thisStatus: MutableLiveData<AsyncStatus> = MutableLiveData(AsyncStatus.Init)
    val thisTag: String = "Log_InicioViewModel"

    fun getMovies(type: Int) {
        isLoading.postValue(true)
        val request = Request.Builder()
            .url(
                if (type == 1) "https://api.themoviedb.org/3/trending/all/week?api_key=0374686862e065e0ff6b049a5e2f9b4b"
                else "https://api.themoviedb.org/3/movie/popular?api_key=0374686862e065e0ff6b049a5e2f9b4b"
            )
            .build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                thisStatus.postValue(AsyncStatus.Failure(e.toString(), 1))
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        try {
                            val itemType = object : TypeToken<MoviesDB>() {}.type
                            db = GsonBuilder().create().fromJson(it.body.string(), itemType)

                            thisStatus.postValue(AsyncStatus.Success("OK", 1))
                        } catch (e: Exception) {
                            Global.logError(thisTag, e.toString())
                            thisStatus.postValue(AsyncStatus.Failure(e.toString(), 1))
                        }
                    } else {
                        thisStatus.postValue(AsyncStatus.Failure("Error: $it", 1))
                    }
                }
            }
        })
    }
}