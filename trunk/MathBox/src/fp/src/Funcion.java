package fp.src;

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
	public double f(double x) {
		return expresion.evaluar(x);
	}
	
	public boolean valida(){
		return !expresion.getSalida().isEmpty();
	}
}
