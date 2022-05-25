package com.ferdiallen.advancedatabasedicoding.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertistOfStudents(student: List<CollegeEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertUniversity(university: List<University>)

    @Insert(onConflict = REPLACE)
    suspend fun insertCourse(course: List<Course>)

    @Query("SELECT * FROM CollegeEntity")
    fun getAllStudents(): Flow<List<CollegeEntity>>

    @Transaction
    @Query("SELECT * FROM CollegeEntity")
    fun getStudentsFromWhichUniversity(): Flow<List<ManyToOne>>

    @Transaction
    @Query("SELECT * FROM CollegeEntity")
    fun getStudentsWithCourse(): Flow<List<StudentWithCourse>>

    @Insert(onConflict = REPLACE)
    suspend fun insertCrossRefData(ref: List<ManyToManyCrossRef>)

    @Delete
    suspend fun deleteSelectedStudents(students: CollegeEntity)

    @RawQuery()
    fun selectStudentByChoice(query: SupportSQLiteQuery): List<CollegeEntity>
}