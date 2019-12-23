package hva.nl.inspiriobot.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import hva.nl.inspiriobot.data.InspirobotRepository
import hva.nl.inspiriobot.data.MindfulnessElement
import hva.nl.inspiriobot.data.MindfulnessResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timerTask

class MindfulnessActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val inspirobotRepository = InspirobotRepository(application.applicationContext)
    val error = MutableLiveData<String>()
    val text = MutableLiveData<String>()
    val stop = MutableLiveData<String>()
    val mindfullness = MutableLiveData<MindfulnessResponse>()

    fun getMindfulnessMode() {
        inspirobotRepository.getSessionId().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                error.value = t.localizedMessage
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() !== null) {
                    inspirobotRepository.getMindfulnessMode(response.body().toString())
                        .enqueue(object : Callback<MindfulnessResponse> {
                            override fun onResponse(
                                call: Call<MindfulnessResponse>,
                                response: Response<MindfulnessResponse>
                            ) {
                               mindfullness.value = response.body()
                            }

                            override fun onFailure(call: Call<MindfulnessResponse>, t: Throwable) {
                                error.value = t.localizedMessage
                            }
                        }

                        )
                } else {
                    error.value = "no value"
                }
            }
        })

    }

    fun processData(data: Array<MindfulnessElement>) {
        data.forEach {
            Timer().schedule(timerTask {
               processElement(it)
            }, it.time.toDouble().toLong() * 1000)
        }

    }

    private fun processElement(it: MindfulnessElement) {
        text.postValue(it.text)
        if (it.type == "stop") {
            stop.postValue("stopped")
        }
    }


}