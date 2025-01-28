package com.api.kebunbinatang.ui.view.hewan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.api.kebunbinatang.ui.customwidget.CostumeTopAppBar
import com.api.kebunbinatang.ui.navigation.DestUpdHewan
import com.api.kebunbinatang.ui.viewmodel.PenyediaVM
import com.api.kebunbinatang.ui.viewmodel.hewan.InsertUiEvent
import com.api.kebunbinatang.ui.viewmodel.hewan.UpdateHewanVM
import com.api.kebunbinatang.ui.viewmodel.hewan.UpdateUiState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateHewanScreen(
    id_hewan: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateHewanVM = viewModel(factory = PenyediaVM .Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    // Load data berdasarkan id_oetugas
    LaunchedEffect(id_hewan) {
        viewModel.loadHewan(id_hewan)
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestUpdHewan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.uiState,
            onHewanValueChange = viewModel::updateInsertHewanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateHewan()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdateBody(
    updateUiState: UpdateUiState,
    onHewanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = updateUiState.insertUiEvent,
            onValueChange = onHewanValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}