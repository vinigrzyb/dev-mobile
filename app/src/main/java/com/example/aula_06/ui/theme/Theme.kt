package com.example.aula_06.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AzulEscuro,
    secondary = AzulTopo,
    tertiary = AzulBorda,
    background = AzulFundo,
    surface = Branco
)

private val DarkColors = darkColorScheme(
    primary = AzulEscuro,
    secondary = AzulTopo,
    tertiary = AzulBorda,
    background = AzulFundo,
    surface = Branco
)

@Composable
fun Aula06Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
