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
fun PantallaTrapecio() {
    var baseMayorInput by remember { mutableStateOf("") }
    var baseMenorInput by remember { mutableStateOf("") }
    var alturaInput by remember { mutableStateOf("") }

    var resultadoArea by remember { mutableStateOf<Double?>(null) }
    var bMayorVal by remember { mutableStateOf<Double?>(null) }
    var bMenorVal by remember { mutableStateOf<Double?>(null) }
    var hVal by remember { mutableStateOf<Double?>(null) }
    var showError by remember { mutableStateOf(false) }

    val WarmBackground = Color(0xFFFFF8F3)
    val CardSurface = Color(0xFFFFFFFF)
    val CuartoColor = Color(0xFFB68530)
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
                text = stringResource(id = R.string.title_trapezoid),
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
                    GraficoTrapecio()
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
                        value = baseMayorInput,
                        onValueChange = { baseMayorInput = it },
                        label = { Text(stringResource(id = R.string.label_base_major)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CuartoColor, focusedLabelColor = CuartoColor
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = baseMenorInput,
                        onValueChange = { baseMenorInput = it },
                        label = { Text(stringResource(id = R.string.label_base_minor)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CuartoColor, focusedLabelColor = CuartoColor
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = alturaInput,
                        onValueChange = { alturaInput = it },
                        label = { Text(stringResource(id = R.string.label_height_trap)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CuartoColor, focusedLabelColor = CuartoColor
                        )
                    )

                    if (showError) {
                        Text(
                            text = stringResource(id = R.string.error_invalid_dim_trap),
                            color = Color(0xFFE05338),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val bMayor = baseMayorInput.toDoubleOrNull()
                            val bMenor = baseMenorInput.toDoubleOrNull()
                            val h = alturaInput.toDoubleOrNull()

                            if (bMayor != null && bMenor != null && h != null && bMayor > 0 && bMenor > 0 && h > 0) {
                                showError = false
                                bMayorVal = bMayor
                                bMenorVal = bMenor
                                hVal = h

                                resultadoArea = ((bMayor + bMenor) / 2) * h
                            } else {
                                showError = true
                                resultadoArea = null
                                bMayorVal = null
                                bMenorVal = null
                                hVal = null
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CuartoColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(stringResource(id = R.string.btn_calculate), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultadoArea != null && bMayorVal != null && bMenorVal != null && hVal != null) {
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
                            text = stringResource(id = R.string.result_trap_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = CuartoColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(id = R.string.step_1_trap),
                            color = TextEspresso,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(id = R.string.step_2_trap, bMayorVal!!, bMenorVal!!, hVal!!),
                            color = TextEspresso.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = WarmBackground, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        val formattedArea = String.format("%.2f", resultadoArea)

                        Text(
                            text = stringResource(id = R.string.step_3_trap, formattedArea),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = CuartoColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun GraficoTrapecio() {
    Image(
        painter = painterResource(id = R.drawable.trapecioimagen),
        contentDescription = stringResource(id = R.string.desc_img_trapecio),
        modifier = Modifier.size(140.dp)
    )
}
