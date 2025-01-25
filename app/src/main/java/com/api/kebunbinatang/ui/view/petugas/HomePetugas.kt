package com.api.kebunbinatang.ui.view.petugas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.api.kebunbinatang.model.Petugas
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.api.kebunbinatang.R
import com.api.kebunbinatang.ui.customwidget.CostumeTopAppBar
import com.api.kebunbinatang.ui.navigation.DestHomePetugas
import com.api.kebunbinatang.ui.viewmodel.PenyediaVM
import com.api.kebunbinatang.ui.viewmodel.hewan.HomeHewanUiState
import com.api.kebunbinatang.ui.viewmodel.petugas.HomePetugasUiState
import com.api.kebunbinatang.ui.viewmodel.petugas.HomePetugasVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetugasHomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(String) -> Unit = {},
    viewModel : HomePetugasVM = viewModel(factory = PenyediaVM.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPetugas()
    }

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestHomePetugas.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPetugas()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Petugas")
            }
        },
    ){innerPadding->
        PetugasHomeStatus(
            homePetugasUiState = viewModel.petugasUiState,
            retryAction = {viewModel.getPetugas()}, Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,

        )
    }
}

@Composable
fun PetugasHomeStatus(
    homePetugasUiState: HomePetugasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    //onDeleteClick: (Petugas) -> Unit = {},
    onDetailClick: (String) -> Unit = {}
) {
    when (homePetugasUiState) {
        is HomePetugasUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePetugasUiState.Success -> {
            if (homePetugasUiState.petugas.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Petugas")
                }
            } else {
                PetugasLayout(
                    petugas = homePetugasUiState.petugas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_petugas)
                    },
                )
            }
        }
        is HomePetugasUiState.Error ->
            OnError( retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier){
    CircularProgressIndicator()
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //CircularProgressIndicator()
        Text(text = "loading FAILED", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = "Rerty")
        }
    }
}


@Composable
fun PetugasLayout(
    petugas: List<Petugas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Petugas) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(petugas) { petugas ->
            PetugasCard(
                petugas = petugas,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onDetailClick(petugas)
            }
        }
    }
}
@Composable
fun PetugasCard(
    petugas: Petugas,
    modifier: Modifier,
    onDetailClick: (Petugas) -> Unit
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = petugas.nama_petugas,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDetailClick(petugas)}) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = ""
                    )
                }
                Text(
                    text = petugas.id_petugas,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = petugas.jabatan,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}