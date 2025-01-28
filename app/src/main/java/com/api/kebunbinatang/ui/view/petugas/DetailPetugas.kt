package com.api.kebunbinatang.ui.view.petugas

import android.R.attr.contentDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.ui.customwidget.CostumeTopAppBar
import com.api.kebunbinatang.ui.navigation.DestDetailPetugas
import com.api.kebunbinatang.ui.viewmodel.PenyediaVM
import com.api.kebunbinatang.ui.viewmodel.petugas.DetailPetugasUiState
import com.api.kebunbinatang.ui.viewmodel.petugas.DetailPetugasVM



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPetugasScreen(
    id_petugas: String,
    navigateBack: () -> Unit,
    onUpdateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPetugasVM = viewModel(factory = PenyediaVM .Factory)
) {
    LaunchedEffect(Unit) {viewModel.getDetailPetugas(id_petugas) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestDetailPetugas.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDetailPetugas(id_petugas)
                }
            )
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.detailUiState,
            retryAction = {viewModel.getDetailPetugas(id_petugas)},
            onDeleteClick = {
                viewModel.deletePetugas(it.id_petugas)
                navigateBack()
            },
            onUpdateClick = onUpdateClick
        )
    }
}



@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailPetugasUiState,
    onDeleteClick: (Petugas) -> Unit,
    onUpdateClick: (String) -> Unit
) {
    when (detailUiState) {
        is DetailPetugasUiState.Loading -> OnLoadingDetailPetugas(modifier = modifier.fillMaxSize())
        is DetailPetugasUiState.Success -> {
            if (detailUiState.petugas.id_petugas.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                DetailContent(
                    petugas = detailUiState.petugas,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = {onDeleteClick(it)},
                    onUpdateClick = onUpdateClick
                )
            }
        }
        is DetailPetugasUiState.Error -> OnErrorDetailPetugas(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun DetailContent(
    petugas: Petugas,
    onUpdateClick: (String) -> Unit,
    onDeleteClick: (Petugas) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "ID: ${petugas.id_petugas}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Nama: ${petugas.nama_petugas}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Jabatan: ${petugas.jabatan}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
                Row {
                    IconButton(onClick = { onUpdateClick(petugas.id_petugas) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Update"
                        )
                    }
                    IconButton(onClick = {onDeleteClick(petugas)}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnLoadingDetailPetugas(modifier: Modifier = Modifier, ){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

    }
}

@Composable
fun OnErrorDetailPetugas(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(text = "loading FAILED", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = "Rerty")
        }
    }
}
