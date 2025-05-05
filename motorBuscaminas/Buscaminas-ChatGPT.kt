import kotlin.random.Random

fun generarCampo(fil: Int, col: Int): MutableList<MutableList<Char>> {
    return MutableList(fil) { MutableList(col) { 'X' } }
}

fun ponerMinas(campo: MutableList<MutableList<Char>>, fil: Int, col: Int, min: Int): MutableList<MutableList<Char>> {
    var minasColocadas = 0
    val totalCeldas = fil * col

    while (minasColocadas < min) {
        val index = Random.nextInt(0, totalCeldas)
        val f = index / col
        val c = index % col

        if (campo[f][c] != 'M') {
            campo[f][c] = 'M'
            minasColocadas++
        }
    }

    return campo
}

class Campo(val fil: Int, val col: Int, m: Int) {
    val campo = generarCampo(fil, col)
    val falso = generarCampo(fil, col)
    val real = ponerMinas(campo, fil, col, m)

    fun coordenadas(f: Int, c: Int): Char = real[f][c]

    fun remplazar(f: Int, c: Int, ch: Char) {
        falso[f][c] = ch
    }

    fun esMina(f: Int, c: Int): Boolean = real[f][c] == 'M'

    fun estaDestapado(f: Int, c: Int): Boolean =
        falso[f][c] != 'X' && falso[f][c] != 'B'

    fun destapar(f: Int, c: Int) {
        if (!estaDestapado(f, c)) {
            falso[f][c] = if (real[f][c] == 'M') 'M' else '_'
        }
    }

    fun ponerBandera(f: Int, c: Int) {
        when (falso[f][c]) {
            'X' -> falso[f][c] = 'B'
            'B' -> falso[f][c] = 'X'
        }
    }

    fun juegoGanado(): Boolean {
        for (i in 0 until fil) {
            for (j in 0 until col) {
                if (real[i][j] != 'M' && falso[i][j] == 'X') return false
            }
        }
        return true
    }
}
