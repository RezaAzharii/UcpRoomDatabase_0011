package com.example.ucproomdatabase.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.ucproomdatabase.ui.customwidget.TopBarApp
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase.ui.viewmodel.dokter.DokUIState
import com.example.ucproomdatabase.ui.viewmodel.dokter.DokterEvent
import com.example.ucproomdatabase.ui.viewmodel.dokter.FormErrorStateDok
import com.example.ucproomdatabase.ui.viewmodel.dokter.InsertDokViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertDokView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertDokViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember{ SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect (uiState.snackBarMessage){
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier = Modifier,
        topBar = {
            TopBarApp(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dokter"
            )
        },
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)}
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            InsertBodyDok(
                uiState = uiState,
                onValueChage = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick ={
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChage: (DokterEvent) -> Unit,
    errorState: FormErrorStateDok = FormErrorStateDok(),
    modifier: Modifier = Modifier
){
    val spesialisList = listOf("Dokter Gigi", "Dokter Umum", "Dokter Bedah", "Ahli Gizi")

    var isDropdownExpanded by remember{ mutableStateOf(false) }
    val selectedSpesialis = dokterEvent.spesialis

    Column (
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.nama,
            onValueChange = {
                onValueChage(dokterEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukan nama")}
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = selectedSpesialis ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Spesialis") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                },
                isError = errorState.spesialis != null,
                placeholder = { Text("Pilih spesialis") }
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                spesialisList.forEach { spesialis ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = spesialis
                            )
                        },
                        onClick = {
                            isDropdownExpanded = false
                            onValueChage(dokterEvent.copy(spesialis = spesialis))
                        }
                    )
                }
            }
        }
        Text(
            text = errorState.spesialis ?: "",
            color = Color.Red,
            modifier = Modifier.padding(top = 4.dp)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.klinik,
            onValueChange = {
                onValueChage(dokterEvent.copy(klinik = it))
            },
            label = { Text("Klinik") },
            isError = errorState.klinik != null,
            placeholder = { Text("Masukan Nama Klinik")}
        )
        Text(
            text = errorState.klinik ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.nohp,
            onValueChange = {
                onValueChage(dokterEvent.copy(nohp = it))
            },
            label = { Text("No Handphone",
                color = Color.Black
            ) },
            isError = errorState.nohp != null,
            placeholder = { Text("Masukan No Handphone anda")}
        )
        Text(
            text = errorState.nohp ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.jamKerja,
            onValueChange = {
                onValueChage(dokterEvent.copy(jamKerja = it))
            },
            label = { Text("Jam-Kerja") },
            isError = errorState.jamKerja != null,
            placeholder = { Text("Masukan Jam Kerja anda")}
        )
        Text(
            text = errorState.jamKerja ?: "",
            color = Color.Red
        )
    }
}


@Composable
fun InsertBodyDok(
    modifier: Modifier = Modifier,
    onValueChage: (DokterEvent) -> Unit,
    uiState: DokUIState,
    onClick: () -> Unit
){
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.bg_kertas),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        ElevatedCard(
            modifier = modifier
                .padding(top = 20.dp)
                .padding(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.whiteSmoke),
                contentColor = colorResource(R.color.black)
            )
        ){
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                FormDokter(
                    dokterEvent = uiState.dokterEvent,
                    onValueChage = onValueChage,
                    errorState = uiState.isEntryyValid,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.sage2)),
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text("Simpan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                }
            }
        }
    }
}