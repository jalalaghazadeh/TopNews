package com.mrjalal.topnews.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mrjalal.topnews.presentation.app.ui.theme.TopNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopNewsTheme {

            }
        }
    }
}