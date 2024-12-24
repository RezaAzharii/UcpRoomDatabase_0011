package com.example.ucproomdatabase.ui.navigation

interface AlamatNavigasi{
    val route: String
}

object DestinasiHomeDok : AlamatNavigasi{
    override val route = "home_dok"
}

object DestinasiInsertDok : AlamatNavigasi {
    override val route: String = "insert_dok"
}

object DestinasiHomeJad : AlamatNavigasi{
    override val route = "home_jad"
}

object DestinasiInsertJad: AlamatNavigasi{
    override val route: String = "insert_jad"
}



