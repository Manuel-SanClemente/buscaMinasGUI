import kotlin.random.Random

fun destapar(campo: Campo):Boolean {
    println("Introduce las coordenadas donde crees que esta la bomba")
    print("Introduce la fila: ")
    var fD = readln().toInt() -1
    print("Introduce la columna: ")
    var cD = readln().toInt() -1
    if (campo.coordenadas(fD,cD) == 'M') { return true }
    else if (campo.coordenadas(fD,cD) == 'B') { return false }
    else { campo.remplazar(fD, cD, '_') }
    return false
}

fun bandera(campo: Campo):Boolean {
    println("Introduce las coordenadas donde crees que esta la bomba")
    print("Introduce la fila: ")
    var fB = readln().toInt() -1
    print("Introduce la columna: ")
    var cB = readln().toInt() -1
    if (campo.coordenadas(fB,cB) == 'M') {
        campo.remplazar(fB, cB, 'B')
        return true
    } else { campo.remplazar(fB, cB, 'B') }
    return false
}


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

class Campo(f:Int, c: Int, m:Int) {
    val campo = generarCampo(f,c)
    val falso = generarCampo(f,c)
    val real =  ponerMinas(campo, f, c, m)

    val fil = f
    val col = c

    fun coordenadas(fF:Int, cC:Int): Char { return real[fF][cC] }
    fun remplazar(fR:Int,cR:Int,chR:Char) { falso[fR][cR] = chR }
}