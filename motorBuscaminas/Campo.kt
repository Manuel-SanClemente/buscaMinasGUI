import kotlin.random.Random

fun generarCampo(fil: Int, col:Int): MutableList<MutableList<Char>> {
    var campo=MutableList(fil){MutableList(col) {'X'} }
    return campo
}

fun ponerMinas(campo: MutableList<MutableList<Char>>, fil:Int, col: Int, min: Int):MutableList<MutableList<Char>> {
    var fila = fil-1
    var columna = col-1
    for (i in 0 until min) {
        var f = Random.nextInt(0, fila)
        var c = Random.nextInt(0, columna)
        campo[f][c] = 'M'
    }
    return campo
}

// ESTA FUNCIÓN SE DEBE MODIFICAR, NO SIRVE PARA COMPOSE PORQUE SOLO IMPRIME POR CONSOLA
fun recorrerCampo(campo: MutableList<MutableList<Char>>, f:Int, c:Int):String {
    var s= ""
    for (i in 0 until f) {
        for (j in 0 until c) {
            print(campo[i][j])
            print(" ")
        }
        println()
    }
    return s
}

class Campo(f:Int, c: Int, m:Int) {
    private val campo = generarCampo(f,c)
    private val falso = generarCampo(f,c)
    private val real =  ponerMinas(campo, f, c, m)

    val fil = f
    val col = c

    fun coordenadas(fF:Int, cC:Int): Char { return real[fF][cC] }
    fun remplazar(fR:Int,cR:Int,chR:Char) { falso[fR][cR] = chR }
    fun mostrar():String { return recorrerCampo(falso, fil, col) } // ESTA FUNCIÓN SE QUITA, NO SIRVE PARA COMPOSE PORQUE SOLO IMPRIME POR CONSOLA
    fun revelar():String { return recorrerCampo(real, fil, col) } // ESTA FUNCIÓN SE QUITA, NO SIRVE PARA COMPOSE PORQUE SOLO IMPRIME POR CONSOLA
}