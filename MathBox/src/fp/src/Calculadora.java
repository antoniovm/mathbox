package fp.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fp.excepciones.ErrorSintacticoExcepcion;
import fp.excepciones.OperadorIncorrectoExcepcion;
import fp.excepciones.ParentesisDesbalanceadosExcepcion;

public class Calculadora {
	static Expresion expresion;
	private static String leido;
	
	public static void main(String[] args) throws IOException {
		do {
			System.out.println("Introduxca una expresion para evaluar");
			leido = leer();
			expresion = new Expresion(leido);
			try {
				expresion.analizarSintaxis();
			} catch (ParentesisDesbalanceadosExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperadorIncorrectoExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErrorSintacticoExcepcion e) {
				e.printStackTrace();
				break;
			}
			expresion.postFija();	//<--------------------por aquii!
			expresion.mostrarInfija();
			System.out.println("Introduxca un valor para evaluar en x");
			System.out.println(expresion.evaluar(Double.parseDouble(leer())));
		} while (!leido.equals("salir"));
	}
	public Calculadora() {
		expresion = new Expresion();
	}
	
	public static String leer() throws IOException {
		return (new BufferedReader(new InputStreamReader(System.in))).readLine();
	}

}
