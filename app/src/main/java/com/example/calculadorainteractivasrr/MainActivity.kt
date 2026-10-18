package com.example.calculadorainteractivasrr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculadorainteractivasrr.ui.theme.CalculadoraInteractivaSRRTheme

val WarmBackground = Color(0xFFFFF8F3)
val CardSurface = Color(0xFFFFFFFF)
val PrimerColor = Color(0xFFFF6B4A)
val SegundoColor = Color(0xFFFFB020)
val TercerColor = Color(0xFFE05338)
val CuartoColor = Color(0xFFB68530)
val TextEspresso = Color(0xFF2D1E18)

sealed class Rutas(val ruta: String) {
    object Inicio : Rutas("inicio")
    object Interes : Rutas("interes")
    object Temperatura : Rutas("temperatura")
    object Prisma : Rutas("prisma")
    object Trapecio : Rutas("trapecio")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraInteractivaSRRTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Rutas.Inicio.ruta) {

                    composable(Rutas.Inicio.ruta) {
                        PantallaInicioWarm(
                            onNavigateToInteres = { navController.navigate(Rutas.Interes.ruta) },
                            onNavigateToTemperatura = { navController.navigate(Rutas.Temperatura.ruta) },
                            onNavigateToPrisma = { navController.navigate(Rutas.Prisma.ruta) },
                            onNavigateToTrapecio = { navController.navigate(Rutas.Trapecio.ruta) }
                            )
                    }
                    composable(Rutas.Interes.ruta) {
                        PantallaInteres()
                    }
                    composable(Rutas.Temperatura.ruta) {
                        PantallaTemperatura()
                    }
                    composable(Rutas.Prisma.ruta){
                        PantallaPrisma()
                    }
                    composable(Rutas.Trapecio.ruta){
                        PantallaTrapecio()
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaInicioWarm(onNavigateToInteres: () -> Unit, onNavigateToTemperatura: () -> Unit, onNavigateToPrisma: () -> Unit, onNavigateToTrapecio: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(top = 48.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_greeting),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextEspresso,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.home_subtitle),
                fontSize = 16.sp,
                color = TextEspresso.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            WarmActionCard(
                titleText = stringResource(id = R.string.title_op1),
                descText = stringResource(id = R.string.desc_op1),
                iconColor = PrimerColor,
                iconDrawableId = R.drawable.logo_interes,
                onClick = onNavigateToInteres
            )

            WarmActionCard(
                titleText = stringResource(id = R.string.title_op2),
                descText = stringResource(id = R.string.desc_op2),
                iconColor = SegundoColor,
                iconDrawableId = R.drawable.logo_temperatura,
                onClick = onNavigateToTemperatura
            )

            WarmActionCard(
                titleText = stringResource(id = R.string.title_op3),
                descText = stringResource(id = R.string.desc_op3),
                iconColor = TercerColor,
                iconDrawableId = R.drawable.logo_prisma,
                onClick = onNavigateToPrisma
            )

            WarmActionCard(
                titleText = stringResource(id = R.string.title_op4),
                descText = stringResource(id = R.string.desc_op4),
                iconColor = CuartoColor,
                iconDrawableId = R.drawable.logo_area,
                onClick = onNavigateToTrapecio
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun WarmActionCard(
    titleText: String,
    descText: String,
    iconColor: Color,
    iconDrawableId: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(iconColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconDrawableId),
                        contentDescription = titleText,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(id = R.string.desc_navigate),
                    tint = TercerColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = titleText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextEspresso
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = descText,
                fontSize = 14.sp,
                color = TextEspresso.copy(alpha = 0.7f),
                lineHeight = 20.sp
            )
        }
    }
}