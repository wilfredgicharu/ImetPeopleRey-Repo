package com.raywenderlich.android.imet.data.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people ORDER BY id DESC")
    fun getAll(): LiveData<List<People>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(people: People)

    @Query("DELETE FROM people")
    fun deleteAll()

    @Query("SELECT * FROM people WHERE id = :id")
    fun find(id: Int): People
}