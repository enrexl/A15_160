package com.api.kebunbinatang.ui.view.hewan

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.ui.customwidget.CostumeTopAppBar
import com.api.kebunbinatang.ui.navigation.DestHomeHewan
import com.api.kebunbinatang.ui.viewmodel.PenyediaVM
import com.api.kebunbinatang.ui.viewmodel.hewan.HomeHewanUiState
import com.api.kebunbinatang.ui.viewmodel.hewan.HomeHewanViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHewanScreen(
    navigateBack: ()-> Unit,
    navigateToItemEntry: ()-> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit={},
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaVM.Factory)
){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val query = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {viewModel.getHewan() }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestHomeHewan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {viewModel.getHewan()}
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = query.value,
                onValueChange = { newText ->
                    query.value = newText
                    viewModel.filterHewan(newText) // Memanggil fungsi filter di ViewModel
                },
                label = { Text("Search by ID Hewan") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            HomeStatus(
                homeHewanUiState = viewModel.hewanUIState,
                retryAction = {viewModel.getHewan()},
                modifier = Modifier,
                onDetailClick = onDetailClick
            )


        }
    }
}


@Composable
fun HomeStatus(
    homeHewanUiState: HomeHewanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Hewan) -> Unit = {},
    onDetailClick: (String) -> Unit = {}
) {
    when (homeHewanUiState) {
        is HomeHewanUiState.Loading -> {OnLoading(modifier = modifier.fillMaxSize())}
        is HomeHewanUiState.Success -> {
            if (homeHewanUiState.hewan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Hewan")
                }
            } else {
                HewanLayout(

                    hewan = homeHewanUiState.hewan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_hewan)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        }
        is HomeHewanUiState.Error -> {OnError(retryAction, modifier = modifier.fillMaxSize())}
    }
}



@Composable
fun HewanLayout(
    hewan: List<Hewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Hewan) -> Unit = {} ,
    onDeleteClick: (Hewan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            items(hewan) { hewan ->
                HewanCard(
                    hewan = hewan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetailClick(hewan) }
                )
            }
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
        CircularProgressIndicator()
        Text(text = "loading FAILED", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = "Rerty")
        }
    }
}

@Composable
fun HewanCard(
    hewan: Hewan,
    modifier: Modifier = Modifier,
    //onDeleteClick: (Hewan) -> Unit = {}
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (hewan.tipe_pakan) {
                "Karnivora" -> Color(0xFFF95454)
                "Herbivora" -> Color(0xFF16C47F)
                "Omnivora" -> Color(0xff77CDFF)
                else -> Color.White
            })
    ) {

        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = hewan.nama_hewan,
                style = MaterialTheme.typography.titleLarge)

            }
            Spacer(modifier.weight(1f))

            Text(text = hewan.populasi,
                style = MaterialTheme.typography.bodyMedium)
            Text(text = hewan.zona_wilayah,
                style = MaterialTheme.typography.titleLarge)

        }
    }
}
