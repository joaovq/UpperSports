package br.com.joaovq.uppersports.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import br.com.joaovq.uppersports.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val FontFamily.Companion.Poppins
    get() = FontFamily(
        Font(GoogleFont("Poppins"), provider)
    )

val FontFamily.Companion.Inter
    get() = FontFamily(
        Font(GoogleFont("Inter"), provider)
    )