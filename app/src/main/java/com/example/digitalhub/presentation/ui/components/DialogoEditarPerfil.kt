package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digitalhub.domain.model.AvatarOptions
import com.example.digitalhub.domain.model.ColorCarta

@Composable
fun IconoEditar(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = "Editar",
        tint = Color.White,
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onClick)
            .padding(4.dp)
    )
}

@Composable
fun DialogoEditarUsername(
    currentUsername: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var username by remember { mutableStateOf(currentUsername) }
    var error by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Username",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    error = null
                },
                label = { Text("Username") },
                isError = error != null,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )

            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        when {
                            username.isBlank() -> error = "Username cannot be empty"
                            username.length < 3 -> error = "Min 3 characters"
                            else -> onConfirm(username)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun DialogoEditarBiografia(
    currentBiografia: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var biografia by remember { mutableStateOf(currentBiografia) }
    var error by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit About You",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = biografia,
                onValueChange = {
                    if (it.length <= 500) {
                        biografia = it
                        error = null
                    } else {
                        error = "Max 500 characters"
                    }
                },
                label = { Text("Biography") },
                isError = error != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 6,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Text(
                text = "${biografia.length}/500",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { onConfirm(biografia) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun DialogoEditarCumpleanos(
    currentCumpleanos: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var cumpleanos by remember { mutableStateOf(currentCumpleanos) }
    var error by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Birthday",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cumpleanos,
                onValueChange = {
                    cumpleanos = it
                    error = null
                },
                label = { Text("DD/MM") },
                placeholder = { Text("25/12") },
                isError = error != null,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )

            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        if (cumpleanos.isBlank()) {
                            onConfirm(cumpleanos)
                        } else {
                            val regex = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])$")
                            if (cumpleanos.matches(regex)) {
                                onConfirm(cumpleanos)
                            } else {
                                error = "Invalid format. Use DD/MM"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun DialogoEditarColorFavorito(
    currentColor: ColorCarta?,
    onDismiss: () -> Unit,
    onConfirm: (ColorCarta?) -> Unit
) {
    var selectedColor by remember { mutableStateOf(currentColor) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Favourite Color",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ColorCarta.values().take(4).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { selectedColor = color }
                                .border(
                                    width = if (selectedColor == color) 4.dp else 2.dp,
                                    color = if (selectedColor == color) Color.Black else Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            ColorPuntoConLetra(color)
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ColorCarta.values().drop(4).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { selectedColor = color }
                                .border(
                                    width = if (selectedColor == color) 4.dp else 2.dp,
                                    color = if (selectedColor == color) Color.Black else Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            ColorPuntoConLetra(color)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedColor = null }
                    .border(
                        width = if (selectedColor == null) 3.dp else 1.dp,
                        color = if (selectedColor == null) Color.Black else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No Color",
                    fontWeight = if (selectedColor == null) FontWeight.Bold else FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { onConfirm(selectedColor) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun DialogoSeleccionarAvatar(
    currentAvatarId: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedAvatar by remember { mutableStateOf(currentAvatarId) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Avatar",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))


            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                items(AvatarOptions.avatares) { avatarId ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(
                                width = if (selectedAvatar == avatarId) 4.dp else 2.dp,
                                color = if (selectedAvatar == avatarId) Color.Black else Color.Gray,
                                shape = CircleShape
                            )
                            .clickable { selectedAvatar = avatarId },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(avatarId),
                            contentDescription = "Avatar",
                            modifier = Modifier.size(70.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { onConfirm(selectedAvatar) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}