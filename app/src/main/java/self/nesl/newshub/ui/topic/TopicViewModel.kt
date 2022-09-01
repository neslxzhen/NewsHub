package self.nesl.newshub.ui.topic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.newshub.data.news.Host
import self.nesl.newshub.data.news.News
import self.nesl.newshub.interactor.GetAllNews
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val getAllNews: GetAllNews,
) : ViewModel() {
    companion object {
        val defaultTopic = TopicNavItems.Square
        val defaultEnableHosts = Host.values().toList()
    }
    private val _topic = MutableStateFlow<TopicNavItems>(defaultTopic)
    private val _enableHosts = MutableStateFlow(defaultEnableHosts)
    val enableHosts = _enableHosts.asStateFlow()
    val newsfeed = _topic
        .flatMapLatest {
            getAllNews.invoke(it)
        }.catch {
            PagingData.empty<News>()
        }.cachedIn(viewModelScope)
        .distinctUntilChanged()

    fun topic(topic: TopicNavItems) {
        this._topic.update { topic }
    }

    fun disableHost(host: Host) {
        this._enableHosts.update { it.minus(host).toSet().toList() }
    }

    fun enableHost(host: Host) {
        this._enableHosts.update { it.plus(host).toSet().toList() }
    }
}