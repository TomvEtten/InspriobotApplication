package hva.nl.inspiriobot.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import hva.nl.inspiriobot.R
import hva.nl.inspiriobot.data.InspirobotQuote
import hva.nl.inspiriobot.data.InspirobotRepository

class SaveActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext!!
    private val inspirobotRepository  = InspirobotRepository(application.applicationContext)
    val error = MutableLiveData<String>()

    fun insertQuote(quote: InspirobotQuote) : Boolean{
        if (quote.title == "" || quote.description == "") {
            error.value = context.getString(R.string.error_empty_field)
            return false
        }

        try {
            inspirobotRepository.saveQuote(quote)
        }catch(e : Exception) {
            error.value = e.message
        }
        return true
    }
}