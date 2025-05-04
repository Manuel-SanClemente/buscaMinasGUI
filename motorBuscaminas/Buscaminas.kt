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