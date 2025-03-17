package com.example.compose_bottomnavbar

import android.media.tv.TsRequest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose_bottomnavbar.ui.theme.Compose_bottomnavbarTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_bottomnavbarTheme {
                val nav = rememberNavController()
                val scope = rememberCoroutineScope()
                val snackBarState = remember { SnackbarHostState() }
                var currentScreen by remember { mutableStateOf(0) }
                val items = listOf(
                    BottomNavItem(
                        Screen.Home.name,
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home
                    ),
                    BottomNavItem(
                        Screen.About.name,
                        selectedIcon = Icons.Filled.AccountBox,
                        unselectedIcon = Icons.Outlined.AccountBox
                    )
                )
                Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
                    SnackbarHost(hostState = snackBarState)
                }, bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = currentScreen == index,
                                onClick = {
                                    currentScreen = index
                                    nav.navigate(item.title)
                                },
                                label = { Text(item.title) },
                                icon = {
                                    BadgedBox(badge = {

                                    }) {
                                        Icon(
                                            imageVector = if (currentScreen == index) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            )
                        }
                    }
                }) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavHost(navController = nav, startDestination = Screen.Home.name) {
                            composable(Screen.Home.name) {
                                Home(scope, snackBarState)
                            }
                            composable(Screen.About.name) {
                                About()
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Screen {
    Home, About
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun Home(scope: CoroutineScope, snack: SnackbarHostState) {
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home")
        Button(onClick = {
            scope.launch {
                val res = snack.showSnackbar("hello", actionLabel = "Click")
                when (res) {
                    SnackbarResult.ActionPerformed -> {
                        Toast.makeText(context, "hh", Toast.LENGTH_SHORT).show()
                    }

                    SnackbarResult.Dismissed -> {

                    }
                }
            }
        }) {
            Text("Show snack bar")
        }
    }
}

@Composable
fun About() {
    Column(
        Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("About")
    }
}