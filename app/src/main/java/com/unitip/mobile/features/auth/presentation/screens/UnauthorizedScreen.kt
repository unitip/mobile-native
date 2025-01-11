package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.R
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.auth.presentation.viewmodels.UnauthorizedViewModel
import com.unitip.mobile.shared.presentation.compositional.LocalNavController

@Composable
fun UnauthorizedScreen(
    viewModel: UnauthorizedViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(32.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.undraw_access_denied),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(fraction = .48f)
                )

                Text(
                    text = "Uh oh!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Text(
                    text = "Terjadi kesalahan pada sesi Anda. Hal ini mungkin disebabkan karena sesi Anda telah berakhir atau tidak valid. Silahkan mencoba untuk masuk ulang",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .alpha(.8f),
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                onClick = {
                    viewModel.clearSession()
                    navController.navigate(AuthRoutes.Index) {
                        popUpTo(AuthRoutes.Unauthorized) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Masuk")
            }
        }
    }
}