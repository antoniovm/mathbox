package fp.src;

public class MathChar {
	
	static final int ERROR = -1, NUM = 0, OPER = 1, PAR_IZ = 2, PAR_DER = 3,
			VAR = 4, AP = 5, CONST = 6, SIGNO = 7;
	
	private String caracter;
	int tipo;
	
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
		if(comprobarNumero(token)) return NUM;
		
		//aplicacion
		if(comprobarAplicacion(token))return AP;
		
		//constantes
		if(comprobarConstante(token))return CONST;
		
		return ERROR;
	}
	/**
	 * Escanea la cadena para comprobar si es un número en su totalidad
	 * @param token
	 * @return
	 */
	public static boolean comprobarNumero(String token) {
		for (int i = 0; i < token.length(); i++) {
			if(!(token.charAt(i)>='0'&&token.charAt(i)<='9'))	//Si se sale de este rango ascii, no es un numero
				return false;
		}
		return true;

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
	/**
	 * Comprueba si la constante es correcta
	 * @param token
	 * @return
	 */
	public static boolean comprobarConstante(String token) {
		switch (token.charAt(0)) {
		case 'e':
			return true;
		case 'p':
			if (token.equals("pi"))
				return true;
			break;

		default:
			break;
		}
		return true;
	}
	/**
	 * Comprueba si la aplicacion es correcta
	 * @param token
	 * @return
	 */
	public static boolean comprobarAplicacion(String token) {
		switch (token.charAt(0)) {
		case 'l':
			if (token.equals("log") || token.equals("ln"))
				return true;
			break;
		case 's':
			if (token.equals("sen") || token.equals("sec"))
				return true;
			break;
		case 'c':
			if (token.equals("cos") || token.equals("cosec")
					|| token.equals("costan"))
				return true;
			break;
		case 't':
			if (token.equals("tan"))
				return true;
			break;

		default:
			break;

		}
		return false;

	}
}
