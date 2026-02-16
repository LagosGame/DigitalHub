package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextfieldUsuario(
    value : String,
    placeholder: String,
    onValueChange:(String)->Unit,
    modifier: Modifier=Modifier,
    keyboardType: KeyboardType= KeyboardType.Text,
    isPassword: Boolean = false,
    enabled: Boolean = true
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = true,
        placeholder = {
            Text(placeholder)
        },
        visualTransformation = if(isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        modifier=modifier,
        shape = RoundedCornerShape(0.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = Color.White.copy(alpha = 0.6f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.4f),
            disabledContainerColor = Color.White.copy(alpha = 0.3f),
            disabledBorderColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)

    )
}