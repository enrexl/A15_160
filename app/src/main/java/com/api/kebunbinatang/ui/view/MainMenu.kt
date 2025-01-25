package com.api.kebunbinatang.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    //toPetugasClick:() -> Unit,
    toHewanClick:() -> Unit,
    //toKandangClick:() -> Unit,
    //toMonitoringClick:() -> Unit

){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text(text = "ZOO Kasihan", style = MaterialTheme.typography.titleLarge )
        Button(onClick = {  },) {
            Text(text = "Petugas")
        }
        Button(onClick = toHewanClick ) {
            Text(text = "Hewan")
        }
        Button(onClick = { }  ) {
            Text(text = "Kandang")
        }
        Button(onClick = { }  ) {
            Text(text = "Monitoring")
        }
//        Button(onClick = toKandangClick ) {
//            Text(text = "Kandang")
//        }
//        Button(onClick = toMonitoringClick ) {
//            Text(text = "Monitoring")
//        }
    }
}