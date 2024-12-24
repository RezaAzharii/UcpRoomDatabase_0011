package com.example.ucproomdatabase.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucproomdatabase.DawgApp
import com.example.ucproomdatabase.ui.viewmodel.dokter.HomeDokViewModel
import com.example.ucproomdatabase.ui.viewmodel.dokter.InsertDokViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.DetailJadViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.HomeJadViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.InsertJadViewModel
import com.example.ucproomdatabase.ui.viewmodel.jadwal.UpdateJadViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer{
            HomeDokViewModel(
                dawgApp().containerApp.repositoryDokter
            )
        }

        initializer {
            InsertDokViewModel(
                dawgApp().containerApp.repositoryDokter
            )
        }

        initializer{
            HomeJadViewModel(
                dawgApp().containerApp.repositoryJadwal
            )
        }

        initializer {
            InsertJadViewModel(
                dawgApp().containerApp.repositoryJadwal,
                dawgApp().containerApp.repositoryDokter
            )
        }

        initializer {
            DetailJadViewModel(
                createSavedStateHandle(),
                dawgApp().containerApp.repositoryJadwal
            )
        }

        initializer {
            UpdateJadViewModel(
                createSavedStateHandle(),
                dawgApp().containerApp.repositoryJadwal,
                dawgApp().containerApp.repositoryDokter
            )
        }
    }
}

fun CreationExtras.dawgApp() : DawgApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DawgApp)