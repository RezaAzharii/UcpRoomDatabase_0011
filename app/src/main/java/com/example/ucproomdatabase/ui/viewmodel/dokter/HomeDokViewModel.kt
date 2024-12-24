package com.example.ucproomdatabase.ui.viewmodel.dokter

import com.example.ucproomdatabase.data.entity.Dokter


data class HomeDokUiState(
    val listDok: List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
