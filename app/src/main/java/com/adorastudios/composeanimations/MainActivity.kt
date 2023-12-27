package com.adorastudios.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adorastudios.composeanimations.screens.MainScreen
import com.adorastudios.composeanimations.screens.screenLazyListWithDragAndDrop.LazyListWithDragAndDropScreen
import com.adorastudios.composeanimations.screens.screenSunAnimation.SunAnimationScreen
import com.adorastudios.composeanimations.ui.theme.ComposeAnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Main.route,
                    ) {
                        composable(
                            route = Screen.Main.route,
                        ) {
                            MainScreen(navController = navController)
                        }
                        composable(
                            route = Screen.LazyListWithDragAndDrop.route,
                        ) {
                            LazyListWithDragAndDropScreen(navController = navController)
                        }
                        composable(
                            route = Screen.SunAnimation.route,
                        ) {
                            SunAnimationScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAnimationsTheme {
        Greeting("Android")
    }
}
