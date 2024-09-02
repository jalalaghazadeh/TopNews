package com.mrjalal.topnews.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrjalal.topnews.presentation.app.ui.theme.TopNewsTheme
import com.mrjalal.topnews.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TopNewsTheme {
                TopNewsApp()
            }
        }

//        val homeVM: HomeViewModel by viewModels<HomeViewModel> {  }
    }
}