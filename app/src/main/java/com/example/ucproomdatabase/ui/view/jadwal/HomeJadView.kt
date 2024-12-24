package com.example.ucproomdatabase.ui.view.jadwal

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.R
import com.example.ucproomdatabase.data.entity.Jadwal
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.HomeJadUiState
import com.example.ucproomdatabase.ui.viewmodel.jadwal.HomeJadViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeJadView(
    viewModel: HomeJadViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddJad: () -> Unit = {},
    onNavigateToHomeDok: () -> Unit = {},
    onCardDetail: (Int) -> Unit = {},
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
                                onClick = onAddJad,
                                containerColor = colorResource(R.color.sage2),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(60.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = ""
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
                                onClick = onNavigateToHomeDok,
                                containerColor = colorResource(R.color.sage2),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(60.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = ""
                                )
                            }
                            Text("Home",
                                color = colorResource(R.color.white),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ){ innerpadding ->
        val homeJadUiState by viewModel.homeJadUiState.collectAsState()

        Column (modifier = Modifier.padding(innerpadding)){
            BodyHomeJadView(
                homeJadUiState = homeJadUiState,
                onClickCardList = {
                    onCardDetail(it)
                },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

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
fun BodyHomeJadView(
    homeJadUiState: HomeJadUiState,
    onClickCardList: (Int) -> Unit = {},
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Box(
        modifier = Modifier.fillMaxHeight()
    ){
        Image(
            painter = painterResource(R.drawable.bg_kertas),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        when{
            homeJadUiState.isLoading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            homeJadUiState.isError -> {
                LaunchedEffect(homeJadUiState.errorMessage) {
                    homeJadUiState.errorMessage?.let { message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                }
            }
            homeJadUiState.listJad.isEmpty() -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Tidak ada Data satupun",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {
                ListJadwal(
                    listJad = homeJadUiState.listJad,
                    onClickList = {
                        onClickCardList(it)
                        println(it)
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun ListJadwal(
    listJad: List<Jadwal>,
    modifier: Modifier = Modifier,
    onClickList: (Int) -> Unit = {}
){
    LazyColumn (
        modifier = modifier
    ){
        items(
            items = listJad,
            itemContent ={ jad ->
                CardJad(
                    jad = jad,
                    onClickCard = {onClickList(jad.idJ)}
                )
            }
        )
    }
}

@Composable
fun CardJad(
    jad: Jadwal,
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit = {}
){
    ElevatedCard (
        onClick = onClickCard,
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
                modifier = modifier.size(100.dp)
            )
            Column (
                modifier = Modifier.padding(8.dp)
            ){
                Row (
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = jad.namaPasien,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row (
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = jad.status,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row (
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = jad.tglKonsul,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row (
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = jad.noHp,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
            }
        }
    }
}