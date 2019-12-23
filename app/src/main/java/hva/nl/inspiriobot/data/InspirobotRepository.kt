package hva.nl.inspiriobot.data

import android.content.Context
import androidx.lifecycle.LiveData
import hva.nl.inspiriobot.SeasonEnum
import hva.nl.inspiriobot.api.InspirobotApi
import hva.nl.inspiriobot.api.InspirobotApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call

class InspirobotRepository(context: Context) {
    private val inspirobotApi: InspirobotApiService = InspirobotApi.createApi()
    private var inspirioDao: InspirioDao = InspirioDatabase.getDatabase(context)!!.InspirioDao()

    fun getRandomQuote(season: String) : Call<ResponseBody> {
        if (season == SeasonEnum.CHRISTMAS.string) {
            return inspirobotApi.getRandomXmasQuote()
        }
        return inspirobotApi.getRandomQuote()
    }

    fun getAllQuotes() : LiveData<List<InspirobotQuote>> {
        return inspirioDao.getAllQuotes()
    }

    fun deleteQuote(inspirobotQuote: InspirobotQuote) {
        CoroutineScope(Dispatchers.IO).launch {
            inspirioDao.deleteQuote(inspirobotQuote)
        }
    }

    fun saveQuote(inspirobotQuote: InspirobotQuote)  {
        CoroutineScope(Dispatchers.IO).launch {
            inspirioDao.insertQuote(inspirobotQuote)
        }
    }

    fun getSessionId() : Call<ResponseBody> {
        return inspirobotApi.getSessionId()
    }

    fun getMindfulnessMode(sessionId : String) : Call<MindfulnessResponse> {
        return inspirobotApi.startMindfulness("1", sessionId)
    }

}