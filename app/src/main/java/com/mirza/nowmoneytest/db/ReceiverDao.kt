package com.mirza.nowmoneytest.db

import androidx.room.*
import com.mirza.nowmoneytest.db.entities.Receiver

@Dao
interface ReceiverDao {


    @Query("SELECT * FROM receiver_table")
    fun getAllReceivers(): List<Receiver>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceiver(receiver: List<Receiver>)

    @Delete
    suspend fun deleteReceiver(receiver: Receiver)

}