package com.sagrd.GamesLibrary.ui.game

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sagrd.GamesLibrary.domain.model.Category
import com.sagrd.facturasapp.Nav.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameFormScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavController
)
{
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Agregar VideoJuego") },
                modifier = Modifier.shadow(16.dp),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(route = AppScreens.FirstScreen.route) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            GameForm(navController=navController,viewModel = viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun GameForm(
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    viewModel: GameViewModel = hiltViewModel(),
    catviewModel: CategoryViewModel = hiltViewModel()
) {
    //Categories
    val categories by categoryViewModel.categories.collectAsStateWithLifecycle()
    //photo picker
    var photoPickerLauncher =  rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->  viewModel.image = uri})
    //Expanded Dropdown bool
    var expanded by remember { mutableStateOf(false) }
    //
    var selectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Ingrese nombre") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(15.dp)) // Create a string value to store the selected city

            Column {


                OutlinedTextField(
                    value = selectedText,
                    onValueChange = { selectedText = it },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            mTextFieldSize = coordinates.size.toSize()
                        },
                    label = {Text("Selecciona la categoria")},
                    trailingIcon = {
                        Icon(icon,"contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(text = { Text(text = category.name)}, onClick = {
                            viewModel.categoryid= category.categoryid!!
                            selectedText=category.name
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Card {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        value = viewModel.image?.lastPathSegment.toString(),
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        leadingIcon = { IconButton(onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                            Icon(imageVector = Icons.Filled.Image, contentDescription ="imagen" )
                        }
                        },
                        label = { Text("Ingrese Imagen") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next)
                    )
                    AsyncImage(model = Uri.parse(Uri.parse(viewModel.image.toString()).toString()),contentScale = ContentScale.Crop, contentDescription =null, modifier = Modifier
                        .size(200.dp)
                        .padding(5.dp) )

                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedButton(onClick = {
                keyboardController?.hide()
                viewModel.SaveGame()
                viewModel.setMessageShown()
                navController.navigate(route = AppScreens.FirstScreen.route)
            }, modifier = Modifier
                .fillMaxWidth()) {
                Icon(imageVector = Icons.Default.Done, contentDescription ="Done" )
            }
        }
        /*
        val categories = listOf(
            Category(categoryid = 1,name = "ACCION"),
            Category(categoryid = 2,name = "PUZZLE"),
            Category(categoryid = 3,name = "INDIE"),
            Category(categoryid = 4,name = "FPS"),
            Category(categoryid = 5,name = "COMPETITIVO"),
            Category(categoryid = 6,name = "MOBA")
        )
        for (category in categories)
        {
            catviewModel.name = category.name
            catviewModel.Save()
        }
         */
    }
}