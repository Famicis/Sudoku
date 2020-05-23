package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;

/***
 * 
 * @author Francesco
 *
 */

public class VentanaSudoku extends JFrame {

	protected static JPanel contentPane;
	protected static JPanel panelMenu;
	protected static JMenuBar menuBar;
	protected static JMenu mnArchivo;
	protected static JMenuItem mntmNuevo;
	protected static JMenuItem mntmCerrar;
	protected static JMenu mnAyuda;
	protected static JMenuItem mntmAyuda;
	protected static JMenuItem mntmAcercaDe;
	protected static JPanel panel;
	protected static JTextField[][] textFieldArray = new JTextField[9][9];
	protected static Sudoku sudoku;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSudoku frame = new VentanaSudoku();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaSudoku() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelMenu = new JPanel();
		contentPane.add(panelMenu, BorderLayout.NORTH);
		
		menuBar = new JMenuBar();
		panelMenu.add(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmNuevo = new JMenuItem("Nuevo");
		mntmNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sudoku = new Sudoku();
				if (isEmpty()) {
					fillTextField();
				} else {
					setEmpty();
					fillTextField();
				}
			}
		});
		mnArchivo.add(mntmNuevo);
		
		mntmCerrar = new JMenuItem("Cerrar");
		mntmCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnArchivo.add(mntmCerrar);
		
		mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		mntmAyuda = new JMenuItem("Ayuda");
		mntmAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cad = "";
				cad+= "Sudoku. \n";
				cad+= "- Nuevo: crea un sudoku al azar. \n";
				cad+= "- Debes rellenar los espacios en blanco. \n";
				cad+= "- Si lo haces mal el programa te alertará. \n";
				cad+= "- Cerrar: cierra el programa. \n";
				cad+= "- AVISO: Si lo cierras no guardarás tu progreso. \n";
				JOptionPane.showMessageDialog(getContentPane(), cad, "Mensaje", JOptionPane.WARNING_MESSAGE);
			}
		});
		mnAyuda.add(mntmAyuda);
		
		mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cad = "";
				cad+= "Versión: 1.1 \n";
				cad+= "Sudoku \n";
				cad+= "Autor: Francesco De Amicis Caballero";
				JOptionPane.showMessageDialog(getContentPane(), cad, "Mensaje", JOptionPane.WARNING_MESSAGE);
			}
		});
		mnAyuda.add(mntmAcercaDe);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(9,9,3, 3));
		
		this.initializeTextField(textFieldArray);
		
	}
	
	/***
	 * Metodo que crea los JTextField y los introduce en el array.
	 * Ajusta el color de la fuente, centra las letras y les añade un KeyListener
	 * @param textFieldArray 
	 * @return un array de JTextField
	 */
	private JTextField[][] initializeTextField(JTextField[][] textFieldArray) {
		//JTextField[][] arrayTextFields = new JTextField[9][9];
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9; j++) {
				textFieldArray[i][j] = new JTextField();
				panel.add(textFieldArray[i][j]);
				textFieldArray[i][j].setColumns(10);
				textFieldArray[i][j].setHorizontalAlignment(JTextField.CENTER);
				textFieldArray[i][j].setForeground(Color.RED);
				textFieldArray[i][j].setName("pos"+i+j);
				textFieldArray[i][j].addKeyListener(new EventoTeclado()); 
			}
		}
		return textFieldArray;
	}	
	
	class EventoTeclado implements KeyListener {

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				JTextField txtFModified = (JTextField)(e.getComponent());
				
				String celda = txtFModified.getName();
				//TODO si el usuario borra un numero incorrecto, pero sin introducir uno nuevo, salta el mensaje de error al no cambiar internamente el valor del array

				Point pos = new Point(toInt(celda.charAt(3)), toInt(celda.charAt(4)));
				int valor = toInt(e.getKeyChar());
				if(valor > 0 && valor <= 9) {
					textFieldArray[pos.x][pos.y].setText(" "+valor); 
					sudoku.setSudValue(pos.x, pos.y, valor); //Introduzco en el array que crea el usuario los valores
				}
				
				//Ahora mismo solo comprueba si el sudoku esta bien en las filas y las columnas
				if(!sudoku.isSudokuOk(pos.x, pos.y)) {
					JOptionPane.showMessageDialog(getContentPane(), "Este numero no cumple con las reglas del sudoku", "Error!", JOptionPane.WARNING_MESSAGE);
				}
					
				if (sudoku.isFull()) {
					JOptionPane.showMessageDialog(getContentPane(), "Felicidades! Has finalizado el sudoku", "Has ganado!", JOptionPane.WARNING_MESSAGE);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		
	}

	/***
	 * Este metodo rellena la mitad de los JTextField
	 * Comprueba dentro de nuestro sudoku que casillas son las que estan ocupadas
	 * Si estan ocupadas, introduce el numero y las deshabilita
	 * Al mismo tiempo, identifico todos los JTextField con un hashcode
	 */
	private void fillTextField() {
		
		for (int i = 0; i<textFieldArray.length; i++) {
			for (int j = 0; j<textFieldArray[i].length; j++) {
				if (sudoku.getCasillasOcupadas()[i][j]) {
					textFieldArray[i][j].setText(Character.toString(sudoku.getTablero()[i][j]));
					textFieldArray[i][j].setEnabled(false);
					textFieldArray[i][j].setDisabledTextColor(Color.BLACK);
					sudoku.setSudValue(i, j, toInt(sudoku.getTablero()[i][j]));
				} 
			}
		}
	}
	
	/***
	 * Este metodo comprueba si el tablero está vacío
	 * Para ello, comprueba si las casillas estan habilitadas
	 * Si hay alguna casilla deshabilitada quiere decir que no esta vacio
	 * @return falso si hay alguna casilla deshabilitada, verdadero si hay alguna habilitada
	 */
	private boolean isEmpty() {
		for (int i = 0; i<textFieldArray.length; i++) {
			for (int j = 0; j<textFieldArray[i].length; j++) {
				if (!(textFieldArray[i][j].isEnabled())) {
					return false;
				}
			}
		}
		return true;
	}
	
	/***
	 * Este metodo reinicializa el tablero
	 */
	private void setEmpty() {
		for (int i = 0; i<textFieldArray.length; i++) {
			for (int j = 0; j<textFieldArray[i].length; j++) {
				textFieldArray[i][j].setEnabled(true);;
				textFieldArray[i][j].setForeground(Color.RED);
				textFieldArray[i][j].setText("");
			}
		}
	}

	public static JTextField[][] getTextFieldArray() {
		return textFieldArray;
	}

	public static void setTextFieldArray(JTextField[][] textFieldArray) {
		VentanaSudoku.textFieldArray = textFieldArray;
	}
	
	private int toInt(char car) {
		switch(car) {
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
			case '4':
				return 4;
			case '5':
				return 5;
			case '6':
				return 6;
			case '7':
				return 7;
			case '8':
				return 8;
			case '9':
				return 9;
			default:
				return -1;
		}
	}
}
