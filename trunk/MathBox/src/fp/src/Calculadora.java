package fp.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fp.excepciones.OperadorIncorrectoExcepcion;

public class Calculadora {
	static Expresion expresion;
	private static String leido;
	
	public static void main(String[] args) throws IOException {
		do {
			System.out.println("Introduxca una expresion para evaluar");
			leido = leer();
			expresion = new Expresion();
			//if(expresion)
			expresion.postFija(leido);
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
