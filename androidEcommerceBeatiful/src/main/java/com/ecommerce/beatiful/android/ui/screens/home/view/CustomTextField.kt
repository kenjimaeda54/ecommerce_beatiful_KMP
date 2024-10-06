package com.ecommerce.beatiful.android.ui.screens.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.beatiful.android.ui.theme.fontsOpenSans

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    placeHolder: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        value = value,
        singleLine = false,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            fontFamily = fontsOpenSans,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        onValueChange = onValueChange) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp),
            leadingIcon = { },
            placeholder = {
                Row(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    Text(
                        text = placeHolder,
                        fontFamily = fontsOpenSans,
                        fontWeight = FontWeight.Light,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.tertiary.copy(0.7f)
                    )
                }

            },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    shape = RoundedCornerShape(CornerSize(7.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                        ),
                    focusedBorderThickness = 0.dp,
                    unfocusedBorderThickness = 0.dp
                )
            }
        )
    }

}



@Preview
@Composable
fun CustomOutlineTextFieldPreview() {
    CustomTextField(
        placeHolder = "Procurar na loja",
        value = "",
        onValueChange = {},

    )

}