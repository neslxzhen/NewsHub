package self.nesl.newshub

import self.nesl.core.interactor.GetAllNews
import self.nesl.core.interactor.GetAllNewsNearby
import self.nesl.core.interactor.GetNews

data class Interactors(
    val getAllNewsNearby: GetAllNewsNearby,
    val getAllNews: GetAllNews,
    val getNews: GetNews,
)