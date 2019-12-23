package hva.nl.inspiriobot.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InspirobotApi {
    companion object {
        private const val baseUrl = "https://inspirobot.me/"

        /**
         * @return [InspirobotApiService] The service class off the retrofit client.
         */
        fun createApi(): InspirobotApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val inspirobotApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return inspirobotApi.create(InspirobotApiService::class.java)
        }
    }
}
