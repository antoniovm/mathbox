package fp.src;

public class MathChar {
	
	static final int ERROR = -1, NUM = 0, OPER = 1, PAR_IZ = 2, PAR_DER = 3,
			VAR = 4, AP = 5, CONST = 6, SIGNO = 7;
	
	private String caracter;
	private int tipo;
	
	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public MathChar() {
		caracter="";
		tipo=-1;
	}
	
	public MathChar(String s) {
		caracter=s;
		tipo=tipoCaracter(s);
	}
	
	
	/**
	 * Devuelve qué tipo de caracter/es matemático es el pasado por parámetro
	 * @param token
	 * @return
	 */
	public static int tipoCaracter(String token) {
		
		if(token.length()==1){	//caracteres unicos
			if(token.charAt(0)=='(')
				return PAR_IZ;
			if(token.charAt(0)==')')
				return PAR_DER;
			if(token.charAt(0)=='x')
				return VAR;
			if((token.charAt(0)=='*')||(token.charAt(0)=='/')||(token.charAt(0)=='^'))
				return OPER;
			if((token.charAt(0)=='+')||(token.charAt(0)=='-'))
				return SIGNO;
		}
		
		
		//cadena numerica
		if(Sintaxis.comprobarNumero(token)) return NUM;
		
		//aplicacion
		if(Sintaxis.comprobarAplicacion(token))return AP;
		
		//constantes
		if(Sintaxis.comprobarConstante(token))return CONST;
		
		return ERROR;
	}
	
	public int getTipo() {
		return tipo;
	}


	@Override
	public String toString() {
		return caracter;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(((MathChar)obj).getTipo()==tipo)
			return (caracter.equals(((MathChar)obj).toString()));
		return false;
	}
	
	public boolean equals(String s) {
		return (caracter.equals(s));
	}
	
}
