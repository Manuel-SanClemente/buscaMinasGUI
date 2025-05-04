import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items

fun obtenerHoraActual(): String {
    val formato = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formato.format(Date())
}

@Composable
fun recorrerCampo(campo: MutableList<MutableList<Char>>, f: Int, c: Int) {
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
            val value = flattenedCampo[index]
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(2.dp)
            ) {
                Button(
                    onClick = { /* Aquí puedes manejar clics en las celdas */ },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = value.toString())
                }
            }
        }
    }
}

@Composable
fun jugar(campo:Campo, m:Int) {
    var minas=m
    recorrerCampo(campo.falso, campo.fil, campo.col)

}



@Composable
@Preview
fun App() {
    MaterialTheme() {
        // Reloj
        var horaActual by remember { mutableStateOf(obtenerHoraActual()) }
        LaunchedEffect(Unit) {
            while (true) {
                horaActual = obtenerHoraActual()
                delay(1000L)
            }
        }

        // Estados del campo
        var filas by remember { mutableStateOf("") }
        var columnas by remember { mutableStateOf("") }
        var minas by remember { mutableStateOf("") }
        var minasInt by remember { mutableStateOf(0) }

        var campo by remember { mutableStateOf<Campo?>(null) }
        var mostrarJuego by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Bienvenido a Buscaminas")
            Text("Reloj: $horaActual")
            Text("Pon el número de filas, columnas y minas del campo")

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

                if (f > 0 && c > 0 && m >= 0) {
                    campo = Campo(f, c, m)
                    minasInt = m
                    mostrarJuego = true
                }
            }) {
                Text("Generar Campo")
            }

            if (mostrarJuego && campo != null) {
                jugar(campo!!, minasInt)
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
