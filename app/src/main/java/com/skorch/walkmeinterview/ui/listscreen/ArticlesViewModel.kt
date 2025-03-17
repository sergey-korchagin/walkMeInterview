package com.skorch.walkmeinterview.ui.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorch.walkmeinterview.domain.Article
import com.skorch.walkmeinterview.domain.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _articles by lazy {
        MutableStateFlow<UiState>(UiState.Loading).also { fetchArticles() }
    }
    val articles: StateFlow<UiState> = _articles.asStateFlow()

    private fun fetchArticles() {
        viewModelScope.launch {
            _articles.emit(UiState.Loading)
            getArticlesUseCase()
                .onStart { _articles.emit(UiState.Loading) }
                .collect { result ->
                    _articles.emit(
                        when {
                            result.isSuccess -> UiState.Success(result.getOrDefault(emptyList()))
                            result.isFailure -> {
                                UiState.Error(
                                    result.exceptionOrNull()?.localizedMessage ?: "Error loading"
                                )
                            }

                            else -> UiState.Error("Unknown error")
                        }
                    )
                }
        }
    }

    fun handleAction(action: Action) {
        when (action) {
            is Action.Reload -> fetchArticles()
        }
    }
}

sealed class Action {
    data object Reload : Action()
}


sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<Article>) : UiState()
    data class Error(val message: String) : UiState()
}