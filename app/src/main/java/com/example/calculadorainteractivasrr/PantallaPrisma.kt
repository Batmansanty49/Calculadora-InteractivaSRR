package com.example.calculadorainteractivasrr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPrisma() {
    var anchoInput by remember { mutableStateOf("") }
    var alturaInput by remember { mutableStateOf("") }
    var profInput by remember { mutableStateOf("") }

    var resultadoVolumen by remember { mutableStateOf<Double?>(null) }
    var wVal by remember { mutableStateOf<Double?>(null) }
    var hVal by remember { mutableStateOf<Double?>(null) }
    var lVal by remember { mutableStateOf<Double?>(null) }
    var showError by remember { mutableStateOf(false) }

    val WarmBackground = Color(0xFFFFF8F3)
    val CardSurface = Color(0xFFFFFFFF)
    val TercerColor = Color(0xFFE05338)
    val TextEspresso = Color(0xFF2D1E18)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.title_prism),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextEspresso
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    GraficoPrisma3D()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    OutlinedTextField(
                        value = anchoInput,
                        onValueChange = { anchoInput = it },
                        label = { Text(stringResource(id = R.string.label_width)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TercerColor, focusedLabelColor = TercerColor
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = alturaInput,
                        onValueChange = { alturaInput = it },
                        label = { Text(stringResource(id = R.string.label_height)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TercerColor, focusedLabelColor = TercerColor
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = profInput,
                        onValueChange = { profInput = it },
                        label = { Text(stringResource(id = R.string.label_depth)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TercerColor, focusedLabelColor = TercerColor
                        )
                    )

                    if (showError) {
                        Text(
                            text = stringResource(id = R.string.error_invalid_dim),
                            color = TercerColor,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val w = anchoInput.toDoubleOrNull()
                            val h = alturaInput.toDoubleOrNull()
                            val l = profInput.toDoubleOrNull()

                            if (w != null && h != null && l != null && w > 0 && h > 0 && l > 0) {
                                showError = false
                                wVal = w
                                hVal = h
                                lVal = l
                                resultadoVolumen = w * h * l
                            } else {
                                showError = true
                                resultadoVolumen = null
                                wVal = null
                                hVal = null
                                lVal = null
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TercerColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(stringResource(id = R.string.btn_calculate), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultadoVolumen != null && wVal != null && hVal != null && lVal != null) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.result_prism_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TercerColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(id = R.string.step_1_prism),
                            color = TextEspresso,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(id = R.string.step_2_prism, wVal!!, hVal!!, lVal!!),
                            color = TextEspresso.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = WarmBackground, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        val formattedVol = String.format("%.2f", resultadoVolumen)

                        Text(
                            text = stringResource(id = R.string.step_3_prism, formattedVol),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TercerColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun GraficoPrisma3D() {
    Image(
        painter = painterResource(id = R.drawable.prismaimagen),
        contentDescription = stringResource(id = R.string.desc_img_prisma),
        modifier = Modifier.size(140.dp)
    )
}
