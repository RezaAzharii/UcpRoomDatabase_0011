package com.example.ucproomdatabase.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Jadwal
import com.example.ucproomdatabase.repository.RepositoryDokter
import com.example.ucproomdatabase.repository.RepositoryJadwal
import com.example.ucproomdatabase.ui.navigation.DestinasiUpdateJad
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJadwal,
    private val repositoryDokter: RepositoryDokter
): ViewModel(){
    var updateUIState by mutableStateOf(JadUIState())
    var dokterList by mutableStateOf<List<String>>(emptyList())
        private set
    private val _idJ: Int = checkNotNull(savedStateHandle[DestinasiUpdateJad.IDJ])

    init{
        getDokterList()
        viewModelScope.launch {
            updateUIState = repositoryJadwal.getJad(_idJ)
                .filterNotNull()
                .first()
                .toUiStateJad()
        }
    }
    private fun getDokterList() {
        viewModelScope.launch {
            repositoryDokter.getAllDok().collect { dokter ->
                dokterList = dokter.map { it.nama }
            }
        }
    }

    fun updateState(jadwalEvent: JadwalEvent){
        updateUIState = updateUIState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFields(): Boolean{
        val event = updateUIState.jadwalEvent
        val errorState = FormErrorStateJad(
            namaPasien =  if(event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp =  if(event.noHp.isNotEmpty()) null else "No Handphone tidak boleh kosong",
            namaDokter =  if(event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            tglKonsul =  if(event.tglKonsul.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            status =  if(event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIState.jadwalEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryJadwal.updateJad(currentEvent.toJadwalEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data Berhasil Diperbarui",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJad()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                }catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal DiPerbarui"
                    )
                }
            }
        }else{
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data Gagal DiPerbarui"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUiStateJad(): JadUIState = JadUIState(
    jadwalEvent = this.toDetailUiEvent()
)