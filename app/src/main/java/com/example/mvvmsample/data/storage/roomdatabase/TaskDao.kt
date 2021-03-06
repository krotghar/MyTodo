package com.example.mvvmsample.data.storage.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvmsample.data.storage.model.RoomModelTask

/**
 * Task dao интерфейс, который предоставляет нам доступ к данным в нашей БД
 *
 * @constructor Create empty Task dao
 */

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<RoomModelTask>

    @Insert
    fun insertTask(task: RoomModelTask)

    @Query("SELECT * FROM task WHERE id=:id")
    fun getTaskById(id: Int): RoomModelTask?

    @Delete
    fun delete(task: RoomModelTask)

    @Query("SELECT COUNT(id) FROM task")
    fun getRowCount(): Int


}