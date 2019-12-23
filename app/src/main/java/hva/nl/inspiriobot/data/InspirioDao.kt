package hva.nl.inspiriobot.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InspirioDao {

    @Query("SELECT * FROM quoteTable")
    fun getAllQuotes(): LiveData<List<InspirobotQuote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: InspirobotQuote)

    @Delete
    suspend fun deleteQuote(quote: InspirobotQuote)

    @Update
    suspend fun updateQuote(quote: InspirobotQuote)

}
