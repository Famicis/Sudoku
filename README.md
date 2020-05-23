# Sudoku
Programa en Java con interfaz gráfica que permite al usuario resolver sudokus <br>

La carpeta Rel8-2 es donde está contenido el sudoku. Hay dos ficheros java: <br>
- VentanaSudoku: contiene la interfaz gráfica <br>
- Sudoku: métodos específicos del sudoku. <br>

La carpeta sudokus contiene un fichero de texto con 3 sudokus (se pueden añadir más). <br>

Al inicializar el programa, se lee el fichero de los sudokus (cada sudoku completo está en una única línea del fichero). <br>
Escoge una línea al azar, se rellena la mitad del sudoku (deshabilitando estas casillas para no poder modificarse) y <br>
deja que el usuario haga el resto. <br>

Si el programa detecta que en una fila, columna o sector has introducido un número que no debías te notifica. <br>
Si lo has completado correctamente, también recibes una notificación.
