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

object DestinasiDetailJad: AlamatNavigasi{
    override val route = "detail_jad"
    const val IDJ = "idJ"
    val routesWithArg = "$route/{$IDJ}"
}

object  DestinasiUpdateJad: AlamatNavigasi{
    override val route = "update_jad"
    const val IDJ = "idJ"
    val routesWithArg = "$route/{$IDJ}"
}

