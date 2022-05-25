package com.ferdiallen.advancedatabasedicoding.utils

import com.ferdiallen.advancedatabasedicoding.data.CollegeEntity
import com.ferdiallen.advancedatabasedicoding.data.Course
import com.ferdiallen.advancedatabasedicoding.data.ManyToManyCrossRef
import com.ferdiallen.advancedatabasedicoding.data.University

object InitialDataSourceSetup {
    fun getUnivData(): List<University> {
        return listOf(
            University(1, "Android University"),
            University(2, "Web University"),
            University(3, "Machine Learning University"),
            University(4, "Cloud University")
        )
    }

    fun getCollegeNamesAndIdentity(): List<CollegeEntity> {
        return listOf(
            CollegeEntity(1, "Andy Rubin", 1),
            CollegeEntity(2, "Rich Miner", 1),
            CollegeEntity(3, "Tim Berners-Lee", 2),
            CollegeEntity(4, "Robert Cailliau", 2),
            CollegeEntity(5, "Arthur Samuel", 3),
            CollegeEntity(6, "JCR Licklider", 4)
        )
    }

    fun getCourse(): List<Course> {
        return listOf(
            Course(1, "Kotlin Basic"),
            Course(2, "Java Basic"),
            Course(3, "Javascript Basic"),
            Course(4, "Python Basic"),
            Course(5, "Dart Basic")
        )
    }

    fun getCourseStudents(): List<ManyToManyCrossRef> {
        return listOf(
            ManyToManyCrossRef(1, 1),
            ManyToManyCrossRef(1, 2),
            ManyToManyCrossRef(2, 2),
            ManyToManyCrossRef(2, 5),
            ManyToManyCrossRef(3, 3),
            ManyToManyCrossRef(4, 3),
            ManyToManyCrossRef(4, 4),
            ManyToManyCrossRef(5, 4),
            ManyToManyCrossRef(6, 3),
            ManyToManyCrossRef(6, 4)
        )
    }
}