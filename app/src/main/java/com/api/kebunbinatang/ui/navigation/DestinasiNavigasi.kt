package com.api.kebunbinatang.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

//main Menu
object  DestMain: DestinasiNavigasi{
    override val route = "homes"
    override val titleRes = "Main Menu"
}
//Petugas
object DestHomePetugas : DestinasiNavigasi{
    override val route = "homePetugas"
    override val titleRes = "Home Petugas"
}
object DestDetailPetugas : DestinasiNavigasi {
    override val route = "detailpetugas"
    override val titleRes = "Detail Petugas"
    const val id_petugas = "id_petugas"
    val routeWithArg = "$route/{$id_petugas}"
}
object DestInsertPetugas : DestinasiNavigasi{
    override val route = "petugas_entry"
    override val titleRes = "Insert Petugas"
}
object DestUpdPetugas : DestinasiNavigasi {
    override val route = "updatePetugas"
    override val titleRes = "Update Petugas"
    const val id_petugas = "id_petugas"
    val routeWithArg = "${DestUpdPetugas.route}/{$id_petugas}"
}

//Hewan
object DestHomeHewan : DestinasiNavigasi{
    override val route = "homeHewan"
    override val titleRes = "Home Hewan"
}
object DestDetailHewan : DestinasiNavigasi {
    override val route = "detailHewan"
    override val titleRes = "Detail Hewan"
    const val id_hewan = "id_hewan"
    val routeWithArg = "$route/{$id_hewan}"
}
object DestInsertHewan : DestinasiNavigasi{
    override val route = "hewan_entry"
    override val titleRes = "Insert Hewan"
}
object DestUpdHewan : DestinasiNavigasi {
    override val route = "updateHewan"
    override val titleRes = "Update Hewan"
    const val id_hewan = "id_hewan"
    val routeWithArg = "${DestUpdHewan.route}/{$id_hewan}"
}
