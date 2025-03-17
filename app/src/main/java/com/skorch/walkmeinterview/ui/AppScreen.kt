package com.skorch.walkmeinterview.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skorch.walkmeinterview.domain.Article
import com.skorch.walkmeinterview.ui.details.ArticleDetailScreen
import com.skorch.walkmeinterview.ui.listscreen.ArticleListScreen
import com.skorch.walkmeinterview.ui.webview.WebViewScreen

@Composable
fun AppScreen(navController: NavHostController) {
    WalkmeinterviewTheme {
        NavHost(navController, startDestination = "articles") {
            composable("articles") {
                ArticleListScreen { article ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                    navController.navigate("details")
                }
            }
            composable("details") {
                val article =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
                article?.let {
                    ArticleDetailScreen(it, navController)
                }
            }
            composable(
                "webview/{url}",
                arguments = listOf(navArgument("url") { type = NavType.StringType })
            ) { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url") ?: ""
                WebViewScreen(url, navController)
            }
        }
    }
}
