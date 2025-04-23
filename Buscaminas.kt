fun check(f:Int, c:Int, m: Int) {
    if (f<=1 && c<=1) { throw Exception("La longitud del campo es invalido") }
    if (m >= (f * c)) { throw Exception("El numero de minas es demasiado grande") }
}

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

class Buscaminas (f: Int, c:Int, m:Int) {
    var C=check(f,c,m)
    var Campo= Campo(f,c,m)
    var juego= jugar(Campo,m)
}