package ru.kredao.learningwords.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import ru.kredao.learningwords.R

@Composable
fun EditDictionaries(
    navController: NavController,
    dictionaries: SnapshotStateList<SnapshotStateMap<String, Any>>,
    editIndex: MutableState<Int?>
) {
    val myFontFamily = FontFamily(
        Font(R.font.montserrat, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )
    var nameDict by remember {
        mutableStateOf("")
    }
    var onCreateItem by remember {
        mutableStateOf(false)
    }
    var items = remember {
        mutableStateListOf(
            mutableStateListOf("Word", "Translate"),
        )
    }
    if (editIndex.value != null) {
        nameDict = dictionaries[editIndex.value!!]["name"].toString()
        items = dictionaries[editIndex.value!!]["dictionary"] as SnapshotStateList<SnapshotStateList<String>>
    }



    fun deleteItem(index: Int) {
        items.removeAt(index)
    }

    fun createItem(word: String, translate: String) {
        items.add(0, mutableStateListOf(word, translate))
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (header, input, add, list, remove) = createRefs()


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .constrainAs(header) {
                    top.linkTo(parent.top, margin = 14.dp)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Create",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontFamily = myFontFamily,
                fontWeight = FontWeight.SemiBold,
            )
            IconButton(onClick = {
                if (nameDict != "" && items.size > 0 && editIndex.value != null) {
                    dictionaries[editIndex.value!!] = mutableStateMapOf(
                            "name" to nameDict,
                            "dictionary" to items
                        )
                    editIndex.value = null
                    navController.navigate("ListDictionaries")
                } else if (nameDict != "" && items.size > 0 && editIndex.value == null) {
                    dictionaries.add(
                        0, mutableStateMapOf(
                            "name" to nameDict,
                            "dictionary" to items
                        )
                    )
                    editIndex.value = null
                    navController.navigate("ListDictionaries")
                }

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        BasicTextField(
            value = nameDict,
            onValueChange = { nameDict = it },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(input) {
                    top.linkTo(header.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = 14.dp)
                },
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 36.sp,
                fontFamily = myFontFamily,
                fontWeight = FontWeight.SemiBold,
            ),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (nameDict == "") {
                        Text(
                            text = "Enter name",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 36.sp,
                            fontFamily = myFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    innerTextField()
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 14.dp)
                .constrainAs(add) {
                    top.linkTo(input.bottom)
                },
            contentAlignment = Alignment.Center
        ) {
            if (onCreateItem) {
                var word by remember {
                    mutableStateOf("Word")
                }
                var translate by remember {
                    mutableStateOf("Translate")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            BasicTextField(
                                value = word,
                                onValueChange = { word = it },
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 20.sp,
                                    fontFamily = myFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                            BasicTextField(
                                value = translate,
                                onValueChange = { translate = it },
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontFamily = myFontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            )

                        }
                        Row {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        if (word != "" && translate != "") {
                                            createItem(word, translate)
                                            onCreateItem = false
                                        }
                                    }
                                    .padding(end = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_check),
                                    contentDescription = "check",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Box(
                                modifier = Modifier.clickable {
                                    onCreateItem = false
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_remove),
                                    contentDescription = "remove",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                        }
                    }
                }
            } else {
                IconButton(onClick = { onCreateItem = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "add",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(add.bottom)
                    bottom.linkTo(remove.top)
                    height = Dimension.fillToConstraints
                }
        ) {
            itemsIndexed(items) { index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            BasicTextField(
                                value = item[0],
                                onValueChange = { item[0] = it },
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 20.sp,
                                    fontFamily = myFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                            BasicTextField(
                                value = item[1],
                                onValueChange = { item[1] = it },
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontFamily = myFontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            )

                        }
                        IconButton(
                            onClick = { deleteItem(index) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_remove),
                                contentDescription = "remove",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }
        }
        IconButton(
            onClick = {
                if (editIndex.value != null) {
                    dictionaries.removeAt(editIndex.value!!)
                    editIndex.value = null
                    navController.navigate("ListDictionaries")
                } else {
                    nameDict = ""
                    items = mutableStateListOf(
                        mutableStateListOf("Word", "Translate"),
                    )
                    navController.navigate("ListDictionaries")
                }

            },
            modifier = Modifier
                .constrainAs(remove) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_remove),
                contentDescription = "remove",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

