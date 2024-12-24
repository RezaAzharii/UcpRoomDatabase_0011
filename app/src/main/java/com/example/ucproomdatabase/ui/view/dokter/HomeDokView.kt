package com.example.ucproomdatabase.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.R
import com.example.ucproomdatabase.data.entity.Dokter
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase.ui.viewmodel.dokter.HomeDokUiState
import com.example.ucproomdatabase.ui.viewmodel.dokter.HomeDokViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeDokView(
    viewModel: HomeDokViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDok: () -> Unit = {},
    onNavigateToHomeJad: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Scaffold (
        topBar = {
            Box (
                modifier = Modifier
            ){
                HeaderDok(
                    modifier = Modifier
                )
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 124.dp)
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.sage1)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            FloatingActionButton(
                                onClick = onAddDok,
                                containerColor = colorResource(R.color.sage2),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(60.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                            Text("Tambah",
                                color = colorResource(R.color.white),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            FloatingActionButton (
                                onClick = onNavigateToHomeJad,
                                containerColor = colorResource(R.color.sage2),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(60.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                            Text("Jadwal",
                                color = colorResource(R.color.white),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ){ innerpadding ->
        val homeDokUiState by viewModel.homeDokUiState.collectAsState()

        Column(modifier = Modifier.padding(innerpadding)) {

            BodyHomeDokView(
                homeDokUiState = homeDokUiState,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderDok(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(190.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.sage0),
            )
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "HealtyDawg",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Image(
                painter = painterResource(R.drawable.bob),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
            )
        }
    }
}


@Composable
fun BodyHomeDokView(
    homeDokUiState: HomeDokUiState,
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Box (
        modifier = Modifier.fillMaxHeight()
    ){
        Image(
            painter = painterResource(R.drawable.bg_kertas),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        when{
            homeDokUiState.isLoading -> {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            homeDokUiState.isError -> {
                LaunchedEffect (homeDokUiState.errorMessage){
                    homeDokUiState.errorMessage?.let{ message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                }
            }
            homeDokUiState.listDok.isEmpty() -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Tidak ada data satupun",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {
                ListDokter(
                    listDok = homeDokUiState.listDok,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun ListDokter(
    listDok: List<Dokter>,
    modifier: Modifier = Modifier,
){
    LazyColumn (
        modifier = modifier
    ){
        items(
            items = listDok,
            itemContent = { dok ->
                CardDok(
                    dok = dok
                )
            }
        )
    }
}

fun ColorSpesial(spesialis: String): Color {
    return when (spesialis) {
        "Dokter Umum" -> Color.Green
        "Dokter Gigi" -> Color.Blue
        "Dokter Bedah" -> Color.Red
        "Ahli Gizi" -> Color.Magenta
        else -> Color.Black
    }
}

//@Preview(showBackground = true)
@Composable
fun CardDok(
    dok: Dokter,
    modifier: Modifier = Modifier,
){
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.whiteSmoke)
        )
    ){
        Row (
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
           Icon(
               imageVector = Icons.Default.AccountCircle,
               contentDescription = null,
               tint = colorResource(R.color.sage0),
               modifier = modifier
                   .size(100.dp)
           )
            Column (
                modifier = Modifier
                    .padding(8.dp)
            ){
                Row (
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = dok.nama,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row (
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = dok.spesialis,
                        color = ColorSpesial(dok.spesialis),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row (
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = dok.klinik,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row (
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = dok.jamKerja,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }

    }
}
