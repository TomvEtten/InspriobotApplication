package hva.nl.inspiriobot.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import hva.nl.inspiriobot.data.InspirobotRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val inspirobotRepository = InspirobotRepository(application.applicationContext)
    val quote = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    fun getRandomQuote(season: String) {
        inspirobotRepository.getRandomQuote(season).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                error.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                quote.value = response.body()?.string()
            }

        })
    }
}