package hva.nl.inspiriobot.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import hva.nl.inspiriobot.data.InspirobotQuote
import hva.nl.inspiriobot.data.InspirobotRepository

class SavedItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val inspirobotRepository = InspirobotRepository(application.applicationContext)
    val quotes = inspirobotRepository.getAllQuotes()

    fun deleteQuote(insiriobotQuote: InspirobotQuote) {
        inspirobotRepository.deleteQuote(insiriobotQuote)
    }
}