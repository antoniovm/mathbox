package fp.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fp.excepciones.ErrorSintacticoExcepcion;
import fp.excepciones.OperadorIncorrectoExcepcion;
import fp.excepciones.ParentesisDesbalanceadosExcepcion;
import fp.excepciones.ValorNoSoportadoException;

public class Calculadora {
	static Expresion expresion;
	private static String leido;
	
	public static void main(String[] args) throws IOException {
		do {
			System.out.println("Introduxca una expresion para evaluar");
			leido = leer();
			expresion = new Expresion();
			try {
				expresion.setCadena(leido);
			} catch (ParentesisDesbalanceadosExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (OperadorIncorrectoExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ErrorSintacticoExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			expresion.mostrarInfija();
			expresion.mostrarPostfija();
			System.out.println("Introduxca un valor para evaluar en x");
			try {
				System.out.println(expresion.evaluar(new MathChar(leer())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErrorSintacticoExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ValorNoSoportadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!leido.equals("salir"));
	}
	public Calculadora() {
		expresion = new Expresion();
	}
	
	public static String leer() throws IOException {
		return (new BufferedReader(new InputStreamReader(System.in))).readLine();
	}

}
