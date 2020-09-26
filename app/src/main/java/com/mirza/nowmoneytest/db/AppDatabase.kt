package com.mirza.nowmoneytest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mirza.nowmoneytest.db.entities.Receiver


@Database(
    entities = [Receiver::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getReceiverDao(): ReceiverDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "NowMoneyDatabase.db"
            ).build()
    }
}
