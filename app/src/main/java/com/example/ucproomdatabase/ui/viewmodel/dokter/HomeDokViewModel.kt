package com.example.ucproomdatabase.ui.viewmodel.dokter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Dokter
import com.example.ucproomdatabase.repository.RepositoryDokter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDokViewModel(
    private val repositoryDokter: RepositoryDokter
) : ViewModel(){
    val homeDokUiState: StateFlow<HomeDokUiState> = repositoryDokter.getAllDok()
        .filterNotNull()
        .map {
            HomeDokUiState(
                listDok = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeDokUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeDokUiState(
                    isLoading = false,
                    isError = false,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDokUiState(
                isLoading = true
            )
        )
}

data class HomeDokUiState(
    val listDok: List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
