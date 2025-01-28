package com.api.kebunbinatang.ui.view.hewan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.ui.customwidget.CostumeTopAppBar
import com.api.kebunbinatang.ui.navigation.DestDetailHewan
import com.api.kebunbinatang.ui.navigation.DestDetailHewan.id_hewan
import com.api.kebunbinatang.ui.viewmodel.PenyediaVM
import com.api.kebunbinatang.ui.viewmodel.hewan.DetailHewanUiState
import com.api.kebunbinatang.ui.viewmodel.hewan.DetailHewanVM
import com.api.kebunbinatang.ui.viewmodel.hewan.HomeHewanViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenHewan(
    id_hewan: String,
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailHewanVM = viewModel(factory = PenyediaVM .Factory)
) {

    LaunchedEffect(Unit) { viewModel.getDetailHewan(id_hewan) }
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestDetailHewan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDetailHewan(id_hewan)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Hewan"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            retryAction = { viewModel.getDetailHewan(id_hewan) },
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.hewanDetailState,
            onDeleteClick = {
                viewModel.deleteHewan(viewModel.hewanDetailState.let { state ->
                    if (state is DetailHewanUiState.Success) state.hewan.id_hewan else ""
                })
                navigateBack()
            }
        )


    }
}




@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailHewanUiState,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is DetailHewanUiState.Loading -> OnLoadingDetail(modifier = modifier.fillMaxSize())
        is DetailHewanUiState.Success -> {
            if (detailUiState.hewan.id_hewan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                ItemDetailHwn(
                    hewan = detailUiState.hewan,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is DetailHewanUiState.Error -> OnErrorDetail(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun ItemDetailHwn(
    modifier: Modifier = Modifier,
    hewan: Hewan,
    onDeleteClick: () -> Unit,
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

    )
        {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ComponentDetailHwn(judul = "ID Hewan", isinya = hewan.id_hewan)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ComponentDetailHwn(judul = "Nama Hewan", isinya = hewan.nama_hewan)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ComponentDetailHwn(judul = "Tipe Pakan", isinya = hewan.tipe_pakan)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ComponentDetailHwn(judul = "Populasi", isinya = hewan.populasi.toString())
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ComponentDetailHwn(judul = "Zona Wilayah", isinya = hewan.zona_wilayah)

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
}


@Composable
fun ComponentDetailHwn(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        })
}


@Composable
fun OnLoadingDetail(modifier: Modifier = Modifier){
    CircularProgressIndicator()
}

@Composable
fun OnErrorDetail(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(text = "loading FAILED", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = "retry")
        }
    }
}


