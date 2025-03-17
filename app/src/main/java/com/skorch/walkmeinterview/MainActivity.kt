package com.skorch.walkmeinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.skorch.walkmeinterview.ui.listscreen.ArticleListScreen
import com.skorch.walkmeinterview.ui.theme.AppScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppScreen(navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ArticleListScreen {

    }
}