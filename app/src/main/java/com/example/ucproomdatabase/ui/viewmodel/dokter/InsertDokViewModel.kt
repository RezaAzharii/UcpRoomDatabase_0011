package com.example.ucproomdatabase.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Dokter
import com.example.ucproomdatabase.repository.RepositoryDokter
import kotlinx.coroutines.launch


class InsertDokViewModel (
    private val repositoryDokter: RepositoryDokter
) : ViewModel(){
    var uiState by mutableStateOf(DokUIState())

    fun updateState(dokterEvent: DokterEvent){
        uiState = uiState.copy(
            dokterEvent = dokterEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.dokterEvent
        val errorState = FormErrorStateDok(
            nama =  if(event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            spesialis = if(event.spesialis.isNotEmpty()) null else " Spesialis tidak boleh kosong",
            klinik = if(event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong",
            nohp = if(event.nohp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            jamKerja = if(event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryyValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.dokterEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDokter.insertDok(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil diSimpan",
                        dokterEvent = DokterEvent(),
                        isEntryyValid = FormErrorStateDok()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal diSimpan"
                    )
                }
            }
        }else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa Kembali data Anda."
            )
        }
    }

    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class DokUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryyValid: FormErrorStateDok = FormErrorStateDok(),
    val snackBarMessage: String? = null
)

data class FormErrorStateDok(
    val nama: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val nohp: String? = null,
    val jamKerja: String? = null
){
    fun isValid(): Boolean{
        return nama == null
                && spesialis == null
                && klinik == null
                && nohp == null
                && jamKerja == null
    }
}

data class DokterEvent(
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val nohp: String = "",
    val jamKerja: String = ""
)

fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    nohp = nohp,
    jamKerja = jamKerja
)