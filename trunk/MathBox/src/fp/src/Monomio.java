package fp.src;

public class Monomio implements Comparable<Monomio>{
	private double coeficiente;
	private int grado;
	
	public Monomio() {
		
	}

	public double getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(double coeficiente) {
		this.coeficiente = coeficiente;
	}

	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}

	public Monomio(double coeficiente, int grado) {
		this.coeficiente=coeficiente;
		this.grado=grado;
	}
	
	public Monomio suma(Monomio m) {
		if(grado==m.getGrado())
			return new Monomio(coeficiente+m.getCoeficiente(),grado);
		return null;

	}
	
	public Monomio resta(Monomio m) {
		if(grado==m.getGrado())
			return new Monomio(coeficiente-m.getCoeficiente(),grado);
		return null;

	}
	public Monomio producto(Monomio m) {
		return new Monomio(coeficiente*m.getCoeficiente(),grado+m.getGrado());
		
	}
	
	public Monomio division(Monomio m) {
		return new Monomio(coeficiente/m.getCoeficiente(),grado-m.getGrado());
		
	}

	@Override
	public int compareTo(Monomio m) {
		if(grado<m.grado) return -1;
		if(grado==m.grado) return 0;
		return 1;
	}
	
	
}
