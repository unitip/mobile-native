package com.unitip.mobile.shared.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.unitip.mobile.shared.presentation.ui.theme.colors.DarkColors
import com.unitip.mobile.shared.presentation.ui.theme.colors.LightColors

// Light Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = LightColors.Primary,
    onPrimary = LightColors.OnPrimary,
    primaryContainer = LightColors.PrimaryContainer,
    onPrimaryContainer = LightColors.OnPrimaryContainer,
    secondary = LightColors.Secondary,
    onSecondary = LightColors.OnSecondary,
    secondaryContainer = LightColors.SecondaryContainer,
    onSecondaryContainer = LightColors.OnSecondaryContainer,
    tertiary = LightColors.Tertiary,
    onTertiary = LightColors.OnTertiary,
    tertiaryContainer = LightColors.TertiaryContainer,
    onTertiaryContainer = LightColors.OnTertiaryContainer,
    error = LightColors.Error,
    onError = LightColors.OnError,
    errorContainer = LightColors.ErrorContainer,
    onErrorContainer = LightColors.OnErrorContainer,
    background = LightColors.Surface,
    onBackground = LightColors.OnSurface,
    surface = LightColors.Surface,
    onSurface = LightColors.OnSurface,
    surfaceVariant = LightColors.SurfaceContainer,
    onSurfaceVariant = LightColors.OnSurfaceVariant,
    outline = LightColors.Outline,
    outlineVariant = LightColors.OutlineVariant,
    inverseSurface = LightColors.InverseSurface,
    inverseOnSurface = LightColors.InverseOnSurface,
    inversePrimary = LightColors.InversePrimary,
    scrim = LightColors.Scrim,
    surfaceTint = LightColors.Primary // Surface Tint biasanya di-set sama dengan Primary
)

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = DarkColors.Primary,
    onPrimary = DarkColors.OnPrimary,
    primaryContainer = DarkColors.PrimaryContainer,
    onPrimaryContainer = DarkColors.OnPrimaryContainer,
    secondary = DarkColors.Secondary,
    onSecondary = DarkColors.OnSecondary,
    secondaryContainer = DarkColors.SecondaryContainer,
    onSecondaryContainer = DarkColors.OnSecondaryContainer,
    tertiary = DarkColors.Tertiary,
    onTertiary = DarkColors.OnTertiary,
    tertiaryContainer = DarkColors.TertiaryContainer,
    onTertiaryContainer = DarkColors.OnTertiaryContainer,
    error = DarkColors.Error,
    onError = DarkColors.OnError,
    errorContainer = DarkColors.ErrorContainer,
    onErrorContainer = DarkColors.OnErrorContainer,
    background = DarkColors.Surface,
    onBackground = DarkColors.OnSurface,
    surface = DarkColors.Surface,
    onSurface = DarkColors.OnSurface,
    surfaceVariant = DarkColors.SurfaceContainer,
    onSurfaceVariant = DarkColors.OnSurfaceVariant,
    outline = DarkColors.Outline,
    outlineVariant = DarkColors.OutlineVariant,
    inverseSurface = DarkColors.InverseSurface,
    inverseOnSurface = DarkColors.InverseOnSurface,
    inversePrimary = DarkColors.InversePrimary,
    scrim = DarkColors.Scrim,
    surfaceTint = DarkColors.Primary // Surface Tint biasanya di-set sama dengan Primary
)



@Composable
fun UnitipTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}