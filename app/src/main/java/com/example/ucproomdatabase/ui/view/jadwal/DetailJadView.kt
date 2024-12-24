package com.example.ucproomdatabase.ui.view.jadwal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.ucproomdatabase.data.entity.Jadwal
import com.example.ucproomdatabase.ui.customwidget.TopBarApp
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.DetailJadViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.DetailUiState
import com.example.ucproomdatabase.ui.viewmodel.jadwal.toJadwalEntity

@Composable
fun DetailJadView(
    modifier: Modifier = Modifier,
    viewModel: DetailJadViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
){
    Scaffold (
        modifier = Modifier,
        topBar = {
            TopBarApp(
                judul = "Detail Jadwal",
                showBackButton = true,
                onBack = onBack
            )
        },
        floatingActionButton ={
            FloatingActionButton(
                onClick = { onEditClick(viewModel.detailUiState.value.detailUiEvent.idJ.toString()) },
                shape = MaterialTheme.shapes.medium,
                containerColor = colorResource(R.color.sage2),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jadwal")
            }
        }
    ){ innerpadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailJad(
            modifier = modifier.padding(innerpadding),
            detailUiState = detailUiState,
            onBodyDeleteClick = {
                viewModel.deleteJad()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailJad(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onBodyDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.bg_kertas),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        when{
            detailUiState.isLoading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            detailUiState.isUiEventNotEmpty -> {
                Column (modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ){
                    ItemDetailJad(
                        jadwal = detailUiState.detailUiEvent.toJadwalEntity(),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.sage2)),
                        onClick = {
                        deleteConfirmationRequired = true
                    }, modifier = Modifier
                        .fillMaxWidth())
                    {
                        Text(text = "Delete",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    if (deleteConfirmationRequired){
                        DeleteConfirmation(
                            onDeleteConfirm = {
                                deleteConfirmationRequired = false
                                onBodyDeleteClick()
                            },
                            onDeleteCancel ={
                                deleteConfirmationRequired = false
                            }, modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            detailUiState.isUiEventEmpty -> {
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Data tidak ditemukan",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailJad(
    modifier: Modifier = Modifier,
    jadwal: Jadwal
){
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.whiteSmoke),
            contentColor = colorResource(R.color.black)
        )
    ){
        Column (
            modifier = modifier.padding(16.dp)
        ){
            ComponentDetailJad(judul = "Nama" , isinya = jadwal.namaPasien)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailJad(judul = "No Handphone" , isinya = jadwal.noHp)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailJad(judul = "Dokter" , isinya = jadwal.namaDokter)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailJad(judul = "Tanggal" , isinya = jadwal.tglKonsul)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailJad(judul = "Status" , isinya = jadwal.status)
        }
    }
}

@Composable
fun ComponentDetailJad(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmation(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data")},
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Delete")
            }
        }
    )
}