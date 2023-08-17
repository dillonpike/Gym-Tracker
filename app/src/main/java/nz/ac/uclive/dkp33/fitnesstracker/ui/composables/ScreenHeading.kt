package nz.ac.uclive.dkp33.fitnesstracker.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ScreenHeading(text: String, textAlign: TextAlign = TextAlign.Left) {
    Text(
        text = text,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface,
        textAlign = textAlign
    )
}