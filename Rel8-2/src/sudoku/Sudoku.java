package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/***
 * 
 * @author Francesco
 *
 */

public class Sudoku {

	//Atributos
	private char[][] solucion = new char[9][9];
	private boolean[][] casillasOcupadas = new boolean [9][9];
	private int[][] sudokuUsuario = new int [9][9];
	
	//Constructor
	public Sudoku() {
		File file = null; 
		Scanner input = null;
		String fileLine ="";
		try {
			file = new File ("./../../sudokus/sudokusResueltos.txt");
			input = new Scanner(file);
			int nLines = countLines(file);	
			fileLine = pickLine(input, fileLine, nLines);	
			fillArray(fileLine);
			this.setHalfTrue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//Getters & setters
	public char[][] getTablero() {
		return solucion;
	}

	public void setTablero(char[][] tablero) {
		this.solucion = tablero;
	}

	public boolean[][] getCasillasOcupadas() {
		return casillasOcupadas;
	}

	public void setCasillasOcupadas(boolean[][] casillasOcupadas) {
		this.casillasOcupadas = casillasOcupadas;
	}
	

	public int[][] getSudokuUsuario() {
		return sudokuUsuario;
	}

	public void setSudokuUsuario(int[][] sudokuUsuario) {
		this.sudokuUsuario = sudokuUsuario;
	}
	
	public void setSudValue(int x, int y, int valor) {
		this.sudokuUsuario[x][y] = valor;
	}

	//Metodos especificos
	/***
	 * Metodo que cuenta las lineas que hay dentro del fichero
	 * @param file archivo que se va a leer
	 * @return el numero de lineas que tiene el archivo
	 * @throws FileNotFoundException si no encontrase el archivo
	 */
	private int countLines(File file) throws FileNotFoundException{
		Scanner contLineas;
		contLineas = new Scanner (file);
		int lineas = 0;
		while(contLineas.hasNext()){
			if (contLineas.hasNextLine()) {
				String aux = contLineas.nextLine();
				lineas++;
			}		
		}
		contLineas.close();
		return lineas;
	}
	
	/***
	 * Metodo que escoge una linea al azar del fichero, generando un numero aleatorio
	 * @param input archivo que vamos a coger
	 * @param fileLine vamos a sobreescribir su valor para contener aqui la linea
	 * @param nLines Numero de lineas que contiene el fichero
	 * @return un String en el que esta contenido nuestro Sudoku
	 */
	private String pickLine(Scanner input, String fileLine, int nLines) {
		int chooseLine = (int)(Math.random()*nLines);	
		while (input.hasNext() && chooseLine >= 0) {
			fileLine = input.nextLine();
			chooseLine--;
			
		}
		fileLine = fileLine.replaceAll("\\s", "");
		return fileLine;
	}
	/***
	 * Metodo que rellena nuestro array de caracteres para crear el sudoku
	 * @param lineFile
	 */
	private void fillArray(String lineFile) {
		int contAux = 0;
		for (int i = 0; i<solucion.length; i++) {
			for (int j = 0; j<solucion[i].length; j++) {
				solucion[i][j] = lineFile.charAt(contAux++);
			}
		}
		
	}
	
	
	
	/**
	 * TODO este codigo se puede mejorar muchisimo
	 * Este metodo rellena marca (en teoria) 40 posiciones del tablero, que son las posiciones donde se va a rellenar automaticamente el sudoku
	 * A veces aplica 40, a veces mas, a veces menos...
	 * Es un desproposito
	 */
	private void setHalfTrue() {
		int contAux = 0; //cuando este contador llegue a 0 no hara falta hacer mas
		while (contAux < 40 ) {	
			for (int i = 0; i<casillasOcupadas.length; i++) {
				for (int j = 0; j< casillasOcupadas[i].length; j++) {
					int aux = (int)(Math.random()*2);
					if (aux == 1) {
						casillasOcupadas[i][j] = true;
						contAux++;
						System.out.println();
						if (contAux == 40) {
							break;
						}
					}
					
				}
				
			}
			
		}
	}
	
	//Este metodo lo tenia solo para hacer comprobaciones
//	public void showSud() {
//		for (int i = 0; i<9; i++) {
//			for (int j = 0; j<9; j++) {
//				System.out.print(sudokuUsuario[i][j]);
//			}
//			System.out.println();
//		}
//	}

	/***
	 * Metodo que busca en la fila, columna, y en todos los sectores
	 * @param row Fila en la que esta
	 * @param column Columna en la que esta
	 * @return 
	 */
	public boolean isSudokuOk(int row, int column) {
		if (isRowOk(row) && isColumnOk(column) && isSectorOk(row, column)) {
			return true;
		}
		return false;
	}
	
	/***
	 * Metodo que comprueba si en la fila se repite algun numero
	 * @param row fila a comprobar
	 * @return verdadero si en la fila no se repite nada, falso si se repite
	 */
	private boolean isRowOk(int row) {
		for (int i = 0; i<sudokuUsuario.length; i++) {
			for (int j = i+1; j<sudokuUsuario.length; j++)
			if (sudokuUsuario[row][i] == sudokuUsuario[row][j] && (sudokuUsuario[row][j] != 0 || sudokuUsuario[row][j] != 0)) {
				return false;
			}
		}
		return true;
	}
	
	/***
	 * Metodo que comprueba si la columna tiene numeros que se repiten
	 * @param column columna a comprobar
	 * @return verdadero si no se repiten, falso si se repite algun numero
	 */
	private boolean isColumnOk(int column) {
		for (int i = 0; i<sudokuUsuario.length; i++) {			
			for (int j = i+1; j<sudokuUsuario.length; j++) {
				if (sudokuUsuario[i][column] == sudokuUsuario[j][column] && (sudokuUsuario[j][column] != 0 || sudokuUsuario[j][column] != 0)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Metodo que comprueba dentro del cuadrado de 3x3 si el numero introducido es correcto
	 * @param row fila a comprobar
	 * @param column columna a comprobar
	 * @return verdadero si no se repite el numero, falso si se repite
	 */
	private boolean isSectorOk(int row, int column) {
	    int sectorRow = (int)(row / 3) * 3; // first row of sector
	    int sectorColumn = (int)(row / 3) * 3; // first column of sector
	    int cont = 0;
	    int[] temporaryArray = createTempArray(sectorRow, sectorColumn, cont);
	    for (int i = 0; i<temporaryArray.length; i++) {        
	    	for (int j = i+1; j<temporaryArray.length; j++) {
	    		if (temporaryArray[i] == temporaryArray[j] && (temporaryArray[i] != 0 || temporaryArray[j] != 0)) {
	    			return false;
	    		}
	    	}
	    }
	    return true;
	}

	private int[] createTempArray(int sectorRow, int sectorColumn, int cont) {
		int[] temporaryArray = new int[9];
	    for (int i = sectorRow; i < (sectorRow+3); i++) {
	    	for(int j = sectorColumn; j < (sectorColumn+3); j++) {
	        	temporaryArray[cont++] = sudokuUsuario[i][j];
	    	}
	    }
		return temporaryArray;
	}
	
	/***
	 * Metodo que comprueba si el sudoku esta completo
	 * @return verdadero si esta completo, falso si no esta completo
	 */
	public boolean isFull() {
		for (int i = 0; i<sudokuUsuario.length; i++) {			
			for (int j = 0; j<sudokuUsuario[i].length; j++) {
				if (sudokuUsuario[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	

	
}
