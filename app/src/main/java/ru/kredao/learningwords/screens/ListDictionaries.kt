package ru.kredao.learningwords.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import ru.kredao.learningwords.R


@Composable
fun ListDictionaries(navController: NavController, dictionaries: SnapshotStateList<SnapshotStateMap<String, Any>>, editIndex: MutableState<Int?>) {
    val myFontFamily = FontFamily(
        Font(R.font.montserrat, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (header, list, add) = createRefs()

        Text(
            text = "Dictionaries",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontFamily = myFontFamily,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(header) {
                start.linkTo(parent.start, margin = 14.dp)
                top.linkTo(parent.top, margin = 14.dp)
            }
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(header.bottom, margin = 29.dp)
                }
        ) {
            itemsIndexed(
                dictionaries
            ) { index, item ->
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
                        Text(
                            text = item["name"].toString(),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                            fontFamily = myFontFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        editIndex.value = index
                                        navController.navigate("EditDictionaries")
                                    }
                                    .padding(end = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit),
                                    contentDescription = "edit",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Box{
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "play",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(add){
                                 top.linkTo(list.bottom, margin = 5.dp)
                },
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                editIndex.value = null
                navController.navigate("EditDictionaries")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}