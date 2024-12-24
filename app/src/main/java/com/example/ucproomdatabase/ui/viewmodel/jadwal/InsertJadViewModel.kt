package com.example.ucproomdatabase.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Jadwal
import com.example.ucproomdatabase.repository.RepositoryDokter
import com.example.ucproomdatabase.repository.RepositoryJadwal
import kotlinx.coroutines.launch

class InsertJadViewModel (
    private val repositoryJadwal: RepositoryJadwal,
    private val repositoryDokter: RepositoryDokter
) : ViewModel(){
    var uiState by mutableStateOf(JadUIState())
    var dokterList by mutableStateOf<List<String>>(emptyList())

    init{
        getDokterList()
    }
    fun updateState(jadwalEvent: JadwalEvent){
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent
        )
    }
    private fun getDokterList() {
        viewModelScope.launch {
            repositoryDokter.getAllDok().collect { dokter ->
                dokterList = dokter.map { it.nama }
            }
        }
    }

    private fun validateFields(): Boolean{
        val event = uiState.jadwalEvent
        val errorState = FormErrorStateJad(
            namaPasien =  if(event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp =  if(event.noHp.isNotEmpty()) null else "No Handphone tidak boleh kosong",
            namaDokter =  if(event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            tglKonsul =  if(event.tglKonsul.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            status =  if(event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.jadwalEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryJadwal.insertJad(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil diSimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJad()
                    )
                }catch (e : Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal diSimpan"
                    )
                }
            }
        }else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Silahkan periksa kembali data Anda."
            )
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class JadUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJad = FormErrorStateJad(),
    val snackBarMessage: String? = null
)

data class FormErrorStateJad(
    val namaPasien: String? = null,
    val noHp: String? = null,
    val namaDokter: String? = null,
    val tglKonsul: String? = null,
    val status: String? = null
){
    fun isValid(): Boolean{
        return namaPasien == null
                && noHp == null
                && namaDokter == null
                && tglKonsul == null
                && status == null
    }
}

data class JadwalEvent(
    val idJ: Int = 0,
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tglKonsul: String = "",
    val status: String = ""
)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    idJ = idJ,
    namaPasien = namaPasien,
    namaDokter = namaDokter,
    noHp = noHp,
    tglKonsul = tglKonsul,
    status = status
)