package fp.src;

import fp.excepciones.ErrorSintacticoExcepcion;
import fp.excepciones.ValorNoSoportadoException;

public class Funcion {
	Expresion expresion;
	
	public Funcion() {
		super();
		expresion=new Expresion();
	}
	public Funcion(Expresion e) {
		super();
		expresion=e;
	}
	public double f(MathChar x) throws ErrorSintacticoExcepcion, ValorNoSoportadoException {
		return expresion.evaluar(x);
	}
	
	public boolean valida(){
		return !expresion.getSalida().isEmpty();
	}
}
