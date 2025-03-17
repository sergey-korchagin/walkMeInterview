package com.skorch.walkmeinterview.data

import com.skorch.walkmeinterview.data.local.ArticleDao
import com.skorch.walkmeinterview.data.local.ArticleEntity
import com.skorch.walkmeinterview.data.remote.ArticleApiService
import com.skorch.walkmeinterview.domain.Article
import com.skorch.walkmeinterview.domain.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApiService,
    private val dao: ArticleDao
) : ArticleRepository {

    override fun getArticles(): Flow<Result<List<Article>>> = flow {
        emit(Result.success(dao.getAllArticles().firstOrNull()?.map { it.toDomain() }.orEmpty()))

        try {
            val response = api.getArticles()
            val articles = response.news.map {
                it.copy(content = Jsoup.parse(it.content).text())  // removes html tags
            }
            dao.insertAll(articles.map { it.toEntity() })
            emit(Result.success(articles))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.distinctUntilChanged()
        .flowOn(Dispatchers.IO)

}


fun ArticleEntity.toDomain(): Article {
    return Article(
        id = this.id,
        title = this.title,
        content = this.content,
        url = this.url,
        author = this.author

    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        url = this.url,
        author = this.author
    )
}