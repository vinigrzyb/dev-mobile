package com.example.aula_06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.aula_06.screens.CadastroReceitaScreen
import com.example.aula_06.screens.DetalheReceitaScreen
import com.example.aula_06.screens.HomeScreen
import com.example.aula_06.screens.LoginScreen
import com.example.aula_06.ui.theme.Aula06Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aula06Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //HomeScreen()
                    //LoginScreen()
                    //DetalheReceitaScreen()
                    CadastroReceitaScreen()
                }
            }
        }
    }
}
