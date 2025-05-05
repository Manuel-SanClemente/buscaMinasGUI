import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

fun obtenerHoraActual(): String {
    val formato = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formato.format(Date())
}

@Composable
fun recorrerCampo(
    campo: MutableList<MutableList<Char>>,
    f: Int,
    c: Int,
    onClick: (Int, Int) -> Unit
) {
    val flattenedCampo = campo.flatten()

    LazyVerticalGrid(
        columns = GridCells.Fixed(c),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(flattenedCampo.size) { index ->
            val row = index / c
            val col = index % c
            val value = campo[row][col]

            Button(
                onClick = { onClick(row, col) },
                modifier = Modifier
                    .size(40.dp)
            ) {
                Text(value.toString())
            }
        }
    }
}

@Composable
fun jugar(
    campo: Campo,
    minas: Int,
    modoBandera: Boolean,
    onPerder: () -> Unit,
    onGanar: () -> Unit
) {
    val campoEstado = remember { mutableStateOf(campo.falso) }

    LaunchedEffect(campoEstado.value) {
        if (campo.juegoGanado()) {
            onGanar()
        }
    }

    recorrerCampo(
        campo = campoEstado.value,
        f = campo.fil,
        c = campo.col
    ) { fila, columna ->
        if (modoBandera) {
            campo.ponerBandera(fila, columna)
        } else {
            if (campo.esMina(fila, columna)) {
                campo.falso[fila][columna] = 'M'
                onPerder()
            } else {
                campo.destapar(fila, columna)
            }
        }
        campoEstado.value = campo.falso.map { it.toMutableList() }.toMutableList()
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var horaActual by remember { mutableStateOf(obtenerHoraActual()) }
        LaunchedEffect(Unit) {
            while (true) {
                horaActual = obtenerHoraActual()
                delay(1000L)
            }
        }

        var filas by remember { mutableStateOf("") }
        var columnas by remember { mutableStateOf("") }
        var minas by remember { mutableStateOf("") }

        var campo by remember { mutableStateOf<Campo?>(null) }
        var mostrarJuego by remember { mutableStateOf(false) }
        var minasInt by remember { mutableStateOf(0) }
        var resultado by remember { mutableStateOf("") }
        var modoBandera by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Bienvenido a Buscaminas")
            Text("Reloj: $horaActual")
            Text("Introduce el número de filas, columnas y minas")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = filas,
                    onValueChange = { filas = it },
                    label = { Text("Filas") }
                )
                TextField(
                    value = columnas,
                    onValueChange = { columnas = it },
                    label = { Text("Columnas") }
                )
                TextField(
                    value = minas,
                    onValueChange = { minas = it },
                    label = { Text("Minas") }
                )
            }

            Button(onClick = {
                val f = filas.toIntOrNull() ?: 0
                val c = columnas.toIntOrNull() ?: 0
                val m = minas.toIntOrNull() ?: 0

                if (f > 0 && c > 0 && m >= 0 && m < f * c) {
                    campo = Campo(f, c, m)
                    minasInt = m
                    mostrarJuego = true
                    resultado = ""
                }
            }) {
                Text("Generar Campo")
            }

            if (mostrarJuego && campo != null) {
                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { modoBandera = !modoBandera }) {
                    Text("Modo: ${if (modoBandera) "Bandera 🚩" else "Descubrir 🔍"}")
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (resultado.isNotEmpty()) {
                    Text(resultado)
                }

                jugar(
                    campo = campo!!,
                    minas = minasInt,
                    modoBandera = modoBandera,
                    onPerder = { resultado = "💥 ¡Perdiste!" },
                    onGanar = { resultado = "🎉 ¡Ganaste!" }
                )
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Buscaminas"
    ) {
        App()
    }
}
