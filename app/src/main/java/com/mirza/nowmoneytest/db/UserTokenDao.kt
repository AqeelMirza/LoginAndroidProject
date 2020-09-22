package com.mirza.nowmoneytest.db

import androidx.room.Dao

@Dao
interface UserTokenDao {

 /*   @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(userToken: UserToken): Long

    @Query("SELECT * FROM UserToken")
    fun getToken(): LiveData<UserToken>*/
}