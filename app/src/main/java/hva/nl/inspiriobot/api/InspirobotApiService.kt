package hva.nl.inspiriobot.api

import hva.nl.inspiriobot.data.MindfulnessResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InspirobotApiService {

    @GET("api?generate=true")
    fun getRandomQuote(): Call<ResponseBody>

    @GET("api?generate=true&season=xmas")
    fun getRandomXmasQuote(): Call<ResponseBody>

    @GET("api?getSessionID=1")
    fun getSessionId(): Call<ResponseBody>

    @GET("/api?")
    fun startMindfulness(@Query("generateFlow") flow: String, @Query("sessionID") sessionId: String): Call<MindfulnessResponse>
}