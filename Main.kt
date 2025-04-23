// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT
// IMPORT


fun jugar(campo:Campo, m:Int) {
    var minas = m
    while (minas != 0) {
        println("Comienza el juego")
        println("1-. Destapar casilla")
        println("2-. Poner bandera")
        println("3-. Rendirse")
        println("$minas minas restantes")
        campo.mostrar()
        print("Escribe ahora tu entrada: ")
        var entrada = readln().toInt()
        if (entrada == 1) {
            if (destapar(campo) == true) {
                println("¡Te ha explotado la mina!")
                campo.revelar()
                break
            }
        } else if (entrada == 2) {
            if (bandera(campo) == true) { minas=minas-1 }
        } else if (entrada == 3) {
            campo.revelar()
            break
        }
    }
}


fun main() {
    println("Bienvenido a Buscaminas.")
    println("Por favor, escribe las dimensiones de tu buscaminas")
    println()
    var jugar=1
    while (jugar == 1) {
        print("Escribe un numero de filas: ")
        var filas = readln().toInt()
        print("Escribe un numero de columnas: ")
        var columnas = readln().toInt()
        print("Escribe el numero de minas que quieres: ")
        var minas = readln().toInt()
        println()
        Buscaminas(filas, columnas, minas)
        println()
        println("Quieres volver a jugar? (S/N)")
        var volver= readln()
        if (volver == "S" || volver == "s") { jugar == 1 }
        else if (volver == "N" || volver == "n") { break }
    }
}