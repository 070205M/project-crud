package com.example.hewannusantara

import androidx.room.*

@Dao
interface HewanDao {
    @Insert
    fun insert(hewan: Hewan)

    @Query("SELECT * FROM hewan")
    fun getAll(): List<Hewan>

    @Query("SELECT * FROM hewan WHERE id = :id LIMIT 1")
    fun getById(id: Int): Hewan?

    @Update
    fun update(hewan: Hewan)

    @Delete
    fun delete(hewan: Hewan)
}
