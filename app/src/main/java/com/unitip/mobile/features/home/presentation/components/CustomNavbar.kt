package com.unitip.mobile.features.home.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User

private data class CustomNavbarItem(
    val icon: ImageVector,
    val title: String,
)

@Composable
fun CustomNavbar() {
    val items = listOf(
        CustomNavbarItem(icon = Lucide.User, title = "hehe"),
        CustomNavbarItem(icon = Lucide.User, title = "hehe"),
        CustomNavbarItem(icon = Lucide.User, title = "hehe"),
        CustomNavbarItem(icon = Lucide.User, title = "hehe"),
        CustomNavbarItem(icon = Lucide.User, title = "hehe"),
    )

    Box(
        modifier = Modifier.border(1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = items.size),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(items) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = it.icon, contentDescription = null)
                    Text(text = it.title,
                        style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomNavbarPreview() {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            CustomNavbar()
        }
    }
}