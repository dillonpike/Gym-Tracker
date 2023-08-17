package nz.ac.uclive.dkp33.fitnesstracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import nz.ac.uclive.dkp33.fitnesstracker.R


@Composable
fun ThreeDotsIconButton(onShareClick: () -> Unit, onDeleteClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        IconButton(
            onClick = { expanded = true }
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onShareClick()
            }) {
                Text(text = stringResource(id = R.string.share_button))
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteClick()
            }) {
                Text(text = stringResource(R.string.delete_button))
            }
        }
    }
}