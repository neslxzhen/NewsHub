package self.nesl.newshub.ui.news_thread

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_thread.*
import self.nesl.newshub.ui.component.AppDialog
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.news.KomicaRePostCard
import self.nesl.newshub.ui.news.KomicaTopNewsCard

@Composable
fun NewsThreadRoute(
    newsThreadViewModel: NewsThreadViewModel,
    navController: NavHostController,
){
    val newsThread by newsThreadViewModel.newsThread.collectAsState(null)
    val loading by newsThreadViewModel.loading.collectAsState(false)
    val replyStack = remember { mutableStateListOf<RePost>() }
    val context = LocalContext.current

    fun onKomicaReplyToClick(replyTo: Paragraph.ReplyTo) {
        newsThread?.rePost
            ?.findLast { it.url.contains(replyTo.id) }
            ?.let { replyStack.add(it) }
    }

    fun onLinkClick(link: Paragraph.Link) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.content))
        startActivity(context, intent, null)
    }

    fun onPreviewReplyTo(replyTo: Paragraph.ReplyTo): String {
        return newsThread?.rePost?.findLast { it.id ==  replyTo.id }?.toText() ?: ""
    }

    NewsThreadScreen(
        refreshState = rememberSwipeRefreshState(loading),
        newsThread = newsThread,
        onRefresh = {
            newsThreadViewModel.refresh()
        },
        navigateUp = { navController.navigateUp() },
        onLinkClick = { onLinkClick(it) },
        onKomicaReplyToClick = { onKomicaReplyToClick(it) },
        onPreviewReplyTo = { onPreviewReplyTo(it) },
    )

    if (replyStack.isNotEmpty()) {
        AppDialog(
            onDismissRequest = {
                replyStack.clear()
            },
        ) {
            RePostCard(
                rePost = replyStack.lastOrNull(),
                onLinkClick = { onLinkClick(it) },
                onKomicaReplyToClick = { onKomicaReplyToClick(it) },
                onPreviewReplyTo = { onPreviewReplyTo(it) },
            )
            Row {
                if (replyStack.size > 1) {
                    Button(onClick = { replyStack.removeLast() }) {
                        Text(text = "Prev")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsThreadScreen(
    refreshState: SwipeRefreshState,
    newsThread: NewsThread? = null,
    onRefresh: () -> Unit,
    navigateUp: () -> Unit = {},
    onLinkClick: (Paragraph.Link) -> Unit = {},
    onKomicaReplyToClick: (Paragraph.ReplyTo) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
){
    Scaffold(
        topBar = {
            NewsHubTopBar(
                onBackPressed = { navigateUp() },
                title = newsThread?.post?.title ?: "",
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = onRefresh,
            ) {
                if (newsThread != null) {
                    LazyColumn {
                        item {
                            when (val head = newsThread.post) {
                                is KomicaTopNews -> KomicaTopNewsCard(
                                    topNews = head,
                                    onLinkClick = onLinkClick,
                                )
                            }
                        }
                        newsThread.rePost.forEach { rePost ->
                            item {
                                RePostCard(
                                    rePost = rePost,
                                    onLinkClick = onLinkClick,
                                    onKomicaReplyToClick = onKomicaReplyToClick,
                                    onPreviewReplyTo = onPreviewReplyTo,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RePostCard(
    rePost: RePost?,
    onLinkClick: (Paragraph.Link) -> Unit = {},
    onKomicaReplyToClick: (Paragraph.ReplyTo) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    when (rePost) {
        is KomicaTopNews -> KomicaRePostCard(
            rePost = rePost,
            onLinkClick = onLinkClick,
            onReplyToClick = onKomicaReplyToClick,
            onPreviewReplyTo = onPreviewReplyTo,
        )
        else -> {}
    }
}