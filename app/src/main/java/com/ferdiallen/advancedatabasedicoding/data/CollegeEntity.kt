package com.ferdiallen.advancedatabasedicoding.data

import androidx.room.*

@Entity
data class CollegeEntity(
    @PrimaryKey val studentId: Int,
    val name: String,
    val univId: Int
)

@Entity
data class University(
    @PrimaryKey val universityId: Int,
    @ColumnInfo(name = "universityName") val name: String
)

@Entity
data class Course(
    @PrimaryKey
    val courseId: Int,
    val name: String
)

data class ManyToOne(
    @Embedded
    val students: CollegeEntity,
    @Relation(
        parentColumn = "univId",
        entityColumn = "universityId"
    )
    val university: University? = null
)

@Entity(primaryKeys = ["studentId", "courseId"])
data class ManyToManyCrossRef(
    val studentId: Int,
    @ColumnInfo(index = true)
    val courseId: Int
)

data class StudentWithCourse(
    @Embedded
    val collegeStudent: CollegeEntity,

    @Relation(
        parentColumn = "studentId",
        entity = Course::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = ManyToManyCrossRef::class,
            parentColumn = "studentId",
            entityColumn = "courseId"
        )
    )
    val course: List<Course>
)