package com.mirza.nowmoneytest.db

/*
@Database(
    entities = [UserToken::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserTokenDao(): UserTokenDao

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
}*/
