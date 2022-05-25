package com.ferdiallen.advancedatabasedicoding

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ferdiallen.advancedatabasedicoding.data.CollegeDatabase
import com.ferdiallen.advancedatabasedicoding.data.CollegeEntity
import com.ferdiallen.advancedatabasedicoding.data.CollegeRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel constructor(app: Application) : AndroidViewModel(app) {
    private val db by lazy {
        CollegeDatabase.getDatabaseInstance(app, viewModelScope)
    }
    private val repo = CollegeRepositories(db.studentsDao())
    val dataFlowOutput = repo.getAllData()
    val dataFlowOutput2 = repo.getManyToOneRelation()
    val dataFlowOutput3 = repo.getStudentWithCourse()
    var dataFlow4 = mutableStateListOf<CollegeEntity>()
    fun deleteSelectedStudents(name: CollegeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteSelectedStudents(name)
    }

    fun searchStudentsName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isEmpty()) {
                dataFlow4.clear()
                return@launch
            }
            val res = repo.getStudentWithName(name)
            for (output in res) {
                if(!dataFlow4.contains(output)){
                    dataFlow4.add(output)
                }
            }
        }
    }

}