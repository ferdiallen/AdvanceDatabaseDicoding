package com.ferdiallen.advancedatabasedicoding.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ferdiallen.advancedatabasedicoding.utils.InitialDataSourceSetup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [CollegeEntity::class, University::class, Course::class, ManyToManyCrossRef::class],
    version = 1,
    autoMigrations = [

    ],
    exportSchema = true
)
abstract class CollegeDatabase : RoomDatabase() {
    abstract fun studentsDao(): StudentsDao

    companion object {
        @Volatile
        private var Instance: CollegeDatabase? = null

        fun getDatabaseInstance(context: Context, appScope: CoroutineScope): CollegeDatabase {
            return Instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, CollegeDatabase::class.java, "db_college")
                        .fallbackToDestructiveMigration()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                appScope.launch(Dispatchers.IO) {
                                    Instance?.let { out ->
                                        out.studentsDao()
                                            .insertistOfStudents(InitialDataSourceSetup.getCollegeNamesAndIdentity())
                                        out.studentsDao()
                                            .insertCourse(InitialDataSourceSetup.getCourse())
                                        out.studentsDao()
                                            .insertUniversity(InitialDataSourceSetup.getUnivData())
                                        out.studentsDao()
                                            .insertCrossRefData(InitialDataSourceSetup.getCourseStudents())
                                    }
                                }
                            }
                        })
                        .build()
                Instance = instance
                instance
            }

        }
    }
}