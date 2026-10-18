package com.example.calculadorainteractivasrr

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow

@Composable
fun PantallaInteres() {
    var capital by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }

    var isSimpleInterest by remember { mutableStateOf(true) }

    var resultadoMonto by remember { mutableStateOf<Double?>(null) }
    var resultadoInteres by remember { mutableStateOf<Double?>(null) }
    var desarrolloFormula by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val WarmBackground = Color(0xFFFFF8F3)
    val CardSurface = Color(0xFFFFFFFF)
    val PrimaryCoral = Color(0xFFFF6B4A)
    val TertiaryTerra = Color(0xFFE05338)
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
                text = stringResource(id = R.string.title_interest),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextEspresso
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WarmToggleButton(
                    text = stringResource(id = R.string.type_simple),
                    isSelected = isSimpleInterest,
                    selectedColor = PrimaryCoral,
                    onClick = { isSimpleInterest = true }
                )
                WarmToggleButton(
                    text = stringResource(id = R.string.type_compound),
                    isSelected = !isSimpleInterest,
                    selectedColor = PrimaryCoral,
                    onClick = { isSimpleInterest = false }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    WarmTextField(
                        value = capital,
                        onValueChange = { capital = it },
                        label = stringResource(id = R.string.label_capital)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    WarmTextField(
                        value = tasa,
                        onValueChange = { tasa = it },
                        label = stringResource(id = R.string.label_rate)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    WarmTextField(
                        value = tiempo,
                        onValueChange = { tiempo = it },
                        label = stringResource(id = R.string.label_time)
                    )

                    if (showError) {
                        Text(
                            text = stringResource(id = R.string.error_invalid_input),
                            color = TertiaryTerra,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val p = capital.toDoubleOrNull()
                            val r = tasa.toDoubleOrNull()
                            val t = tiempo.toDoubleOrNull()

                            if (p != null && r != null && t != null && p > 0 && r > 0 && t > 0) {
                                showError = false
                                val rDecimal = r / 100

                                if (isSimpleInterest) {
                                    val monto = p * (1 + (rDecimal * t))
                                    resultadoMonto = monto
                                    resultadoInteres = monto - p
                                    desarrolloFormula = "M = $p * [1 + ($r/100) * $t]\nM = $p * [1 + ${rDecimal * t}]"
                                } else {
                                    val monto = p * (1 + rDecimal).pow(t)
                                    resultadoMonto = monto
                                    resultadoInteres = monto - p
                                    desarrolloFormula = "M = $p * [1 + ($r/100)]^$t\nM = $p * [${1 + rDecimal}]^$t"
                                }
                            } else {
                                showError = true
                                resultadoMonto = null
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryCoral),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(stringResource(id = R.string.btn_calculate), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultadoMonto != null && resultadoInteres != null) {
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
                            text = stringResource(id = R.string.result_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PrimaryCoral
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = if (isSimpleInterest) stringResource(id = R.string.formula_simple) else stringResource(id = R.string.formula_compound),
                            color = TextEspresso,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = desarrolloFormula,
                            color = TextEspresso.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = WarmBackground, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        val formattedMonto = String.format("%.2f", resultadoMonto)
                        val formattedInteres = String.format("%.2f", resultadoInteres)

                        Text(
                            text = stringResource(id = R.string.result_interest, formattedInteres),
                            fontSize = 16.sp,
                            color = TextEspresso
                        )
                        Text(
                            text = stringResource(id = R.string.result_amount, formattedMonto),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TertiaryTerra
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun WarmTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF6B4A),
            focusedLabelColor = Color(0xFFFF6B4A)
        )
    )
}

@Composable
fun WarmToggleButton(text: String, isSelected: Boolean, selectedColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) selectedColor else Color(0xFFFFF0E6),
            contentColor = if (isSelected) Color.White else Color(0xFFE05338)
        ),
        shape = RoundedCornerShape(50),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (isSelected) 4.dp else 0.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}