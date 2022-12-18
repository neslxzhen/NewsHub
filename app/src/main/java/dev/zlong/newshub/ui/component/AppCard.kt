package dev.zlong.newshub.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import dev.zlong.newshub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCard(
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            content()
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            content()
        }
    }
}