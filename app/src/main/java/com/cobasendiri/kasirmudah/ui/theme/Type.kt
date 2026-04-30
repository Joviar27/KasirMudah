package com.cobasendiri.kasirmudah.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import com.cobasendiri.kasirmudah.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Poppins")
val poppinsFontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Medium),
)

val baseline = Typography()

val KasirMudahTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = poppinsFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = poppinsFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = poppinsFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = poppinsFontFamily),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        color = OnPrimary
    ), //28.sp
    headlineSmall = baseline.headlineSmall.copy(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        color = OnPrimary
    ), //24.sp
    titleLarge = baseline.titleLarge.copy(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = OnPrimary
    ),//24.sp
    titleMedium = baseline.titleMedium.copy(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = OnPrimary
    ),//16.sp
    titleSmall = baseline.titleSmall.copy(fontFamily = poppinsFontFamily),
    bodyLarge = baseline.bodyLarge.copy(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        color = OnPrimary
    ),//16.sp
    bodyMedium = baseline.bodyMedium.copy(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        color = OnPrimary
    ),//14.sp
    bodySmall = baseline.bodySmall.copy(fontFamily = poppinsFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = poppinsFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = poppinsFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = poppinsFontFamily),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

