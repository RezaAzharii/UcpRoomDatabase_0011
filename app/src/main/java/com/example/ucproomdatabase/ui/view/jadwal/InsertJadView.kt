package com.example.ucproomdatabase.ui.view.jadwal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.R
import com.example.ucproomdatabase.ui.customwidget.TopBarApp
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.FormErrorStateJad
import com.example.ucproomdatabase.ui.viewmodel.jadwal.InsertJadViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.JadUIState
import com.example.ucproomdatabase.ui.viewmodel.jadwal.JadwalEvent
import kotlinx.coroutines.launch

@Composable
fun InsertJadView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJadViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect (
        uiState.snackBarMessage
    ){
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
                judul = "Tambah Jadwal"
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){  padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            InsertBodyJad(
                uiState = uiState,
                dokterList = viewModel.dokterList,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
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
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    dokterList: List<String>,
    onValueChange: (JadwalEvent) -> Unit,
    errorStateJad: FormErrorStateJad = FormErrorStateJad(),
    modifier: Modifier = Modifier
){
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien = it))
            },
            label = { Text("Nama Pasien") },
            isError = errorStateJad.namaPasien != null,
            placeholder = { Text("Masukan nama anda")}
        )
        Text(
            text = errorStateJad.namaPasien ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.noHp,
            onValueChange = {
                onValueChange(jadwalEvent.copy(noHp = it))
            },
            label = { Text("Nomor Pasien") },
            isError = errorStateJad.noHp != null,
            placeholder = { Text("Masukan nomor Handphone anda")}
        )
        Text(
            text = errorStateJad.noHp ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(4.dp))

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = {isDropdownExpanded = it}
        ) {
            OutlinedTextField(
                modifier =Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = jadwalEvent.namaDokter,
                onValueChange = {},
                readOnly = true,
                label = { Text("Nama Dokter") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                },
                isError = errorStateJad.namaDokter != null,
                placeholder = { Text("Pilih Dokter")}
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                dokterList.forEach { dokter ->
                    DropdownMenuItem(
                        text = { Text(dokter) },
                        onClick = {
                            isDropdownExpanded = false
                            onValueChange(jadwalEvent.copy(namaDokter = dokter))
                        }
                    )
                }
            }
        }
        Text(
            text = errorStateJad.namaDokter ?: "",
            color = Color.Red,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tglKonsul,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tglKonsul = it))
            },
            label = { Text("Tanggal") },
            isError = errorStateJad.tglKonsul != null,
            placeholder = { Text("Masukan Tanggal Konsultasi")}
        )
        Text(
            text = errorStateJad.tglKonsul ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.status,
            onValueChange = {
                onValueChange(jadwalEvent.copy(status = it))
            },
            label = { Text("Status Penanganan") },
            isError = errorStateJad.status != null,
            placeholder = { Text("Masukan Status Penanganan")}
        )
        Text(
            text = errorStateJad.status ?: "",
            color = Color.Red
        )
    }
}

@Composable
fun InsertBodyJad(
    modifier: Modifier = Modifier,
    dokterList: List<String>,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadUIState,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
            .fillMaxHeight()
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
                modifier = modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                FormJadwal(
                    jadwalEvent = uiState.jadwalEvent,
                    dokterList = dokterList,
                    onValueChange = onValueChange,
                    errorStateJad = uiState.isEntryValid,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.sage2)),
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Simpan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            }
        }
    }
}