package com.example.tracklog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tracklog.data.Training
import com.example.tracklog.data.Competition
import com.example.tracklog.data.Converters
import com.example.tracklog.data.TrackLogDao

@Database(entities = [Training::class, Competition::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TrackLogDatabase : RoomDatabase() {

    abstract fun trackLogDao(): TrackLogDao

    companion object {
        @Volatile
        private var Instance: TrackLogDatabase? = null

        val MIGRATION_3_4 = object : androidx.room.migration.Migration(3, 4) {
            override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                // 1. Create new table
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `competition_table_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `date` INTEGER NOT NULL,
                        `name` TEXT NOT NULL,
                        `location` TEXT NOT NULL,
                        `events` TEXT NOT NULL
                    )
                    """.trimIndent()
                )

                // 2. Copy data
                val cursor = db.query("SELECT id, date, name, event, result FROM competition_table")
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                    val date = cursor.getLong(cursor.getColumnIndexOrThrow("date"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val event = cursor.getString(cursor.getColumnIndexOrThrow("event"))
                    val result = cursor.getString(cursor.getColumnIndexOrThrow("result"))

                    // Create JSON for events list.
                    // Note: We are manually creating JSON here to avoid Gson dependency inside Migration if possible,
                    // but since Gson is used in Converters, we could use it if we had access.
                    // However, simple string manipulation is safer for raw SQL migration.
                    // Structure: [{"name":"...","performance":"...","distanceMeters":null,"wind":null,"isInvalid":false}]
                    val eventsJson = """[{"name":"$event","performance":"$result","distanceMeters":null,"wind":null,"isInvalid":false}]"""

                    db.execSQL(
                        "INSERT INTO competition_table_new (id, date, name, location, events) VALUES (?, ?, ?, ?, ?)",
                        arrayOf(id, date, name, "AL", eventsJson) // Default location AL
                    )
                }
                cursor.close()

                // 3. Drop old table
                db.execSQL("DROP TABLE competition_table")

                // 4. Rename new table
                db.execSQL("ALTER TABLE competition_table_new RENAME TO competition_table")
            }
        }

        fun getDatabase(context: Context): TrackLogDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackLogDatabase::class.java, "tracklog_database")
                    .addMigrations(MIGRATION_3_4)
                    .fallbackToDestructiveMigration() // Keep this as fallback
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
