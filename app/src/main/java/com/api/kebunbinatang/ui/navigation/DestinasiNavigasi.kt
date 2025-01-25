package com.api.kebunbinatang.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

//Hewan
object DestHomeHewan : DestinasiNavigasi{
    override val route = "home"
    override val titleRes = "Home Mhs"
}
object DestDetailHewan : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Hewan"
    const val id_hewan = "id_hewan"
    val routeWithArg = "$route/{$id_hewan}"
}
object DestInsertHewan : DestinasiNavigasi{
    override val route = "hewan_entry"
    override val titleRes = "Insert Hewan"
}
object DestUpdHewan : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Hewan"
    const val id_hewan = "id_hewan"
    val routeWithArg = "${DestDetailHewan.route}/{$id_hewan}"
}
