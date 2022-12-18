package self.nesl.newshub.ui.comment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.comment.Comment
import self.nesl.hub_server.data.comment.gamer.GamerComment
import self.nesl.hub_server.data.comment.gamer.mockGamerComment
import self.nesl.newshub.toHumanTime
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.theme.NewshubTheme

@Composable
fun CommentListRoute(
    commentListViewModel: CommentListViewModel,
    navController: NavHostController,
){
    val pagingComments = commentListViewModel.pagingComments.collectAsLazyPagingItems()
    var loading by remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(loading)

    CommentListScreen(
        refreshState = refreshState,
        pagingComments = pagingComments,
        onLoadStateChange = { loadState ->
            loadState.apply {
                when {
                    refresh is LoadState.Loading -> {
                        loading = true
                    }
                    refresh is LoadState.NotLoading -> {
                        loading = false
                    }
                    refresh is LoadState.Error -> {
                        loading = false
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentListScreen(
    refreshState: SwipeRefreshState,
    pagingComments: LazyPagingItems<Comment>,
    onLoadStateChange: (CombinedLoadStates) -> Unit = { },
){
    Scaffold {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = { },
            ) {
                LazyColumn {
                    pagingComments.apply {
                        item {
                            onLoadStateChange(loadState)
                        }
                        items(this) { comment ->
                            if (comment != null ) {
                                CommentCell(comment)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentCell(comment: Comment) {
    when (comment) {
        is GamerComment -> GamerCommentCell(comment)
    }
}