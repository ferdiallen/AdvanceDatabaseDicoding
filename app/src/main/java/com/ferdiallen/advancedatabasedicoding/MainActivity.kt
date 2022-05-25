package com.ferdiallen.advancedatabasedicoding

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferdiallen.advancedatabasedicoding.data.Course
import com.ferdiallen.advancedatabasedicoding.ui.theme.AdvanceDatabaseDicodingTheme
import com.ferdiallen.advancedatabasedicoding.utils.SelectorState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvanceDatabaseDicodingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreenComposable()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenComposable(vm: MainViewModel = viewModel()) {
    val data = vm.dataFlowOutput.collectAsState(initial = emptyList())
    val data2 = vm.dataFlowOutput2.collectAsState(initial = emptyList())
    val data3 = vm.dataFlowOutput3.collectAsState(initial = emptyList())
    val searchData = vm.dataFlow4
    var textSearch by remember {
        mutableStateOf("")
    }
    var selectorMode by remember {
        mutableStateOf("Normal")
    }
    val focusTextField = LocalFocusManager.current
    var hasFocus by remember {
        mutableStateOf(false)
    }
    BackHandler {
        focusTextField.clearFocus()
        hasFocus = false
    }
    if(hasFocus){
        vm.searchStudentsName(textSearch)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
    ) {
        Text("Students Data", fontSize = 28.sp)
        LazyRow(Modifier.fillMaxWidth()) {
            item {
                Button(onClick = { selectorMode = SelectorState.selectorMode[1] }) {
                    Text("Many To One Mode")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { selectorMode = SelectorState.selectorMode[0] }) {
                    Text("Normal")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { selectorMode = SelectorState.selectorMode[2] }) {
                    Text("Student With Course")
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextField(value = textSearch, onValueChange = {
            textSearch = it
        }, modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    hasFocus = true
                    selectorMode = SelectorState.selectorMode[3]
                }
            })
        LazyColumn {
            when (selectorMode) {
                SelectorState.selectorMode[0] -> {
                    items(data.value) { out ->
                        ItemsCardList(out.name) {
                            vm.deleteSelectedStudents(out)
                        }
                    }
                }
                SelectorState.selectorMode[1] -> {
                    items(data2.value) { out ->
                        ItemsCardList(out.students.name, out.university?.name.toString()) {
                            vm.deleteSelectedStudents(out.students)
                        }
                    }
                }
                SelectorState.selectorMode[2] -> {
                    items(data3.value) { out ->
                        ItemsCardList(out.collegeStudent.name, dataList = out.course) {
                            vm.deleteSelectedStudents(out.collegeStudent)
                        }
                    }
                }
                SelectorState.selectorMode[3] -> {
                    items(searchData) { out ->
                        ItemsCardList(out.name) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemsCardList(
    name: String = "",
    univName: String = "",
    dataList: List<Course>? = null,
    onClick: () -> Unit
) {
    var concatenatedCourse by remember {
        mutableStateOf("")
    }
    dataList?.let {
        val lastIndex = it.last()
        it.forEach { out ->
            concatenatedCourse += if (out.name == lastIndex.name) {
                out.name
            } else {
                "${out.name}, "
            }
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 12.dp)
            .clickable { onClick.invoke() }, elevation = 8.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 12.dp)
        ) {
            Text(text = name, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (univName.isNotEmpty()) univName else concatenatedCourse,
                fontSize = 18.sp,
                color = Color.LightGray
            )
        }
    }
}