package com.ferdiallen.advancedatabasedicoding.data

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow

class CollegeRepositories(private val db: StudentsDao) {
    fun getAllData(): Flow<List<CollegeEntity>> = db.getAllStudents()
    fun getManyToOneRelation(): Flow<List<ManyToOne>> = db.getStudentsFromWhichUniversity()
    fun getStudentWithCourse(): Flow<List<StudentWithCourse>> = db.getStudentsWithCourse()
    suspend fun deleteSelectedStudents(data: CollegeEntity) = db.deleteSelectedStudents(data)
    fun getStudentWithName(name: String): List<CollegeEntity> {
        val queryAppender =
            StringBuilder().append("SELECT * FROM CollegeEntity WHERE name LIKE '$name%'")
        return db.selectStudentByChoice(SimpleSQLiteQuery(queryAppender.toString()))
    }
}