package com.mrtnmrls.devhub.common.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mrtnmrls.devhub.common.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.common.ui.theme.CadetBlue
import com.mrtnmrls.devhub.common.ui.theme.DarkElectricBlue
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.JapaneseIndigo

@Composable
fun DevCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    isEnabled: Boolean = true,
    onCheckedChange: () -> Unit
) {
    Checkbox(
        modifier = modifier,
        checked = isChecked,
        enabled = isEnabled,
        onCheckedChange = { onCheckedChange() },
        colors = CheckboxColors(
            checkedCheckmarkColor = JapaneseIndigo,
            checkedBoxColor = AzureishWhite,
            checkedBorderColor = JapaneseIndigo,
            uncheckedCheckmarkColor = JapaneseIndigo,
            uncheckedBoxColor = AzureishWhite,
            uncheckedBorderColor = JapaneseIndigo,
            disabledBorderColor = DarkElectricBlue,
            disabledCheckedBoxColor = CadetBlue,
            disabledUncheckedBoxColor = CadetBlue,
            disabledUncheckedBorderColor = DarkElectricBlue,
            disabledIndeterminateBoxColor = CadetBlue,
            disabledIndeterminateBorderColor = DarkElectricBlue
        )
    )
}

@Preview
@Composable
private fun PreviewDevCheckbox() {
    DevhubTheme {
        Surface {
            Column {
                DevCheckbox { }
                DevCheckbox(
                    isChecked = true
                ) { }
                DevCheckbox(
                    isEnabled = false
                ) { }
                DevCheckbox(
                    isChecked = true,
                    isEnabled = false
                ) { }
            }
        }
    }
}
