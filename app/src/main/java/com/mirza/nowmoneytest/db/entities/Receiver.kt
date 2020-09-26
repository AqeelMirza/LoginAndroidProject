package com.mirza.nowmoneytest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receiver_table")
data class Receiver(
    @PrimaryKey(autoGenerate = false)
    var _id: String,
    var name: String,
    var number: String,
    var address: String
)