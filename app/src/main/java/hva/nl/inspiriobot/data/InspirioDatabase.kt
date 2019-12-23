package hva.nl.inspiriobot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [InspirobotQuote::class], version = 1, exportSchema = false)
abstract class InspirioDatabase : RoomDatabase() {

    abstract fun InspirioDao(): InspirioDao

    companion object {
        private const val DATABASE_NAME = "INSPIRIO_BOT"

        @Volatile
        private var reminderRoomDatabaseInstance: InspirioDatabase? = null

        fun getDatabase(context: Context): InspirioDatabase? {
            if (reminderRoomDatabaseInstance != null) {
                return reminderRoomDatabaseInstance
            }
            synchronized(InspirioDatabase::class.java) {
                reminderRoomDatabaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    InspirioDatabase::class.java, DATABASE_NAME
                )
                    .build()
            }
            return reminderRoomDatabaseInstance
        }
    }

}