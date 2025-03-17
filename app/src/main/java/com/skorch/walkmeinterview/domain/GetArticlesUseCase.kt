package com.skorch.walkmeinterview.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val repository: ArticleRepository) {
    operator fun invoke(): Flow<Result<List<Article>>> {
        return repository.getArticles()
    }
}