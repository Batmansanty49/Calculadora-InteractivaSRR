package com.example.calculadorainteractivasrr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaTemperatura() {
    var temperaturaInput by remember { mutableStateOf("") }
    var unidadOrigen by remember { mutableStateOf("C") }
    var unidadDestino by remember { mutableStateOf("F") }

    var resultadoFinal by remember { mutableStateOf<Double?>(null) }
    var desarrolloFormula by remember { mutableStateOf("") }
    var formulaBase by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val WarmBackground = Color(0xFFFFF8F3)
    val CardSurface = Color(0xFFFFFFFF)
    val SegundoColor = Color(0xFFFFB020)
    val TextEspresso = Color(0xFF2D1E18)
    val ErrorColor = Color(0xFFE05338)

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
                text = stringResource(id = R.string.title_temperature),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextEspresso
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    WarmTextField(
                        value = temperaturaInput,
                        onValueChange = { temperaturaInput = it },
                        label = stringResource(id = R.string.label_temperature)
                    )

                    if (mensajeError.isNotEmpty()) {
                        Text(
                            text = mensajeError,
                            color = ErrorColor,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(stringResource(id = R.string.label_from), fontWeight = FontWeight.Bold, color = TextEspresso)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        WarmToggleButton("°C", unidadOrigen == "C", SegundoColor) { unidadOrigen = "C" }
                        WarmToggleButton("°F", unidadOrigen == "F", SegundoColor) { unidadOrigen = "F" }
                        WarmToggleButton("K", unidadOrigen == "K", SegundoColor) { unidadOrigen = "K" }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(stringResource(id = R.string.label_to), fontWeight = FontWeight.Bold, color = TextEspresso)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        WarmToggleButton("°C", unidadDestino == "C", SegundoColor) { unidadDestino = "C" }
                        WarmToggleButton("°F", unidadDestino == "F", SegundoColor) { unidadDestino = "F" }
                        WarmToggleButton("K", unidadDestino == "K", SegundoColor) { unidadDestino = "K" }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val errAbsC = stringResource(id = R.string.error_abs_c)
                    val errAbsF = stringResource(id = R.string.error_abs_f)
                    val errAbsK = stringResource(id = R.string.error_abs_k)
                    val errInvalid = stringResource(id = R.string.error_invalid_temp)
                    val formulaSameUnit = stringResource(id = R.string.formula_same_unit)

                    Button(
                        onClick = {
                            val temp = temperaturaInput.toDoubleOrNull()
                            mensajeError = ""
                            resultadoFinal = null

                            if (temp == null) {
                                mensajeError = errInvalid
                                return@Button
                            }

                            when (unidadOrigen) {
                                "C" -> if (temp < -273.15) { mensajeError = errAbsC; return@Button }
                                "F" -> if (temp < -459.67) { mensajeError = errAbsF; return@Button }
                                "K" -> if (temp < 0.0) { mensajeError = errAbsK; return@Button }
                            }

                            if (unidadOrigen == unidadDestino) {
                                resultadoFinal = temp
                                formulaBase = formulaSameUnit
                                desarrolloFormula = "$temp = $temp"
                            } else {
                                when ("$unidadOrigen->$unidadDestino") {
                                    "C->F" -> {
                                        resultadoFinal = (temp * 9 / 5) + 32
                                        formulaBase = "°F = (°C × 9/5) + 32"
                                        desarrolloFormula = "°F = ($temp × 1.8) + 32\n°F = ${temp * 1.8} + 32"
                                    }
                                    "C->K" -> {
                                        resultadoFinal = temp + 273.15
                                        formulaBase = "K = °C + 273.15"
                                        desarrolloFormula = "K = $temp + 273.15"
                                    }
                                    "F->C" -> {
                                        resultadoFinal = (temp - 32) * 5 / 9
                                        formulaBase = "°C = (°F - 32) × 5/9"
                                        desarrolloFormula = "°C = ($temp - 32) × 0.555\n°C = ${temp - 32} × 0.555"
                                    }
                                    "F->K" -> {
                                        resultadoFinal = (temp - 32) * 5 / 9 + 273.15
                                        formulaBase = "K = (°F - 32) × 5/9 + 273.15"
                                        desarrolloFormula = "K = ($temp - 32) × 0.555 + 273.15\nK = ${(temp - 32) * 5 / 9} + 273.15"
                                    }
                                    "K->C" -> {
                                        resultadoFinal = temp - 273.15
                                        formulaBase = "°C = K - 273.15"
                                        desarrolloFormula = "°C = $temp - 273.15"
                                    }
                                    "K->F" -> {
                                        resultadoFinal = (temp - 273.15) * 9 / 5 + 32
                                        formulaBase = "°F = (K - 273.15) × 9/5 + 32"
                                        desarrolloFormula = "°F = ($temp - 273.15) × 1.8 + 32\n°F = ${(temp - 273.15) * 1.8} + 32"
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SegundoColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(stringResource(id = R.string.btn_calculate), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultadoFinal != null) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.result_temp_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = SegundoColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = formulaBase, color = TextEspresso, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = desarrolloFormula, color = TextEspresso.copy(alpha = 0.7f), fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = WarmBackground, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        val formattedResult = String.format("%.2f", resultadoFinal)

                        Text(
                            text = stringResource(id = R.string.result_temp_format, temperaturaInput, unidadOrigen),
                            fontSize = 14.sp,
                            color = TextEspresso
                        )
                        Text(
                            text = stringResource(id = R.string.result_temp_final, formattedResult, unidadDestino),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = SegundoColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
