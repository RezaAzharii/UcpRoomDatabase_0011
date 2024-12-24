package com.example.ucproomdatabase.dependenciesinjection

import android.content.Context
import com.example.ucproomdatabase.data.database.EastDawg
import com.example.ucproomdatabase.repository.LocalRepositoryDokter
import com.example.ucproomdatabase.repository.LocalRepositoryJadwal
import com.example.ucproomdatabase.repository.RepositoryDokter
import com.example.ucproomdatabase.repository.RepositoryJadwal

interface InterfaceContainerApp{
    val repositoryDokter: RepositoryDokter
    val repositoryJadwal: RepositoryJadwal
}

class ContainerApp (private val context: Context) : InterfaceContainerApp{
    override val repositoryDokter: RepositoryDokter by lazy {
        LocalRepositoryDokter(EastDawg.getDatabase(context).dokterDao())
    }
    override val repositoryJadwal: RepositoryJadwal by lazy {
        LocalRepositoryJadwal(EastDawg.getDatabase(context).jadwalDao())
    }
}