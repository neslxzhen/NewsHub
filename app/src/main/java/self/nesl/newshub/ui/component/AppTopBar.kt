package self.nesl.newshub.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.AppDarkBlue
import self.nesl.newshub.ui.theme.AppWhite
import self.nesl.newshub.ui.theme.PreviewTheme

@Composable
fun NewsHubTopBar(
    title: String = "",
    onMenuPressed: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    TopAppBar {
        Box(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.space_32)),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    if (onBackPressed != null) {
                        IconButton(
                            onClick = { onBackPressed() }
                        ) {
                            Icon(Icons.Outlined.ArrowBack, "arrow_back")
                        }
                    }
                    if (onMenuPressed != null) {
                        IconButton(
                            onClick = { onMenuPressed() }
                        ) {
                            Icon(Icons.Outlined.Menu, "menu")
                        }
                    }
                    Text(
                        text = title,
                        style = NewshubTheme.typography.h6,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNewsHubTopBar() {
    PreviewTheme {
        NewsHubTopBar("NewsHub")
    }
}

@Preview
@Composable
private fun PreviewNewsHubTopBarWithBack() {
    PreviewTheme {
        NewsHubTopBar(
            title = "NewsHub",
            onBackPressed = { },
        )
    }
}

@Preview
@Composable
private fun PreviewNewsHubTopBarWithMenu() {
    PreviewTheme {
        NewsHubTopBar(
            title = "NewsHub",
            onMenuPressed = { },
        )
    }
}