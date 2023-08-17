package nz.ac.uclive.dkp33.fitnesstracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import nz.ac.uclive.dkp33.fitnesstracker.R
import nz.ac.uclive.dkp33.fitnesstracker.ui.theme.LightLightGray

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(R.string.text_field_placeholder),
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    textAlign: TextAlign = TextAlign.Left,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    width: Dp = 80.dp
) {
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color.DarkGray
    } else {
        LightLightGray
    }
    BasicTextField(
        modifier = modifier
            .background(
                backgroundColor,
                MaterialTheme.shapes.small,
            )
            .width(width),
        value = value,
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            textAlign = textAlign
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
            }
        }
    )
}