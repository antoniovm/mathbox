package fp.src;

public class MathChar {
	
	static final int NUM=0, OPER=1, PAR_IZ=2,PAR_DER=3, VAR=4; 
	private String caracter;
	int tipo;
	
	public MathChar() {
		caracter="";
		tipo=-1;
	}
	
	public MathChar(String s) {
		caracter=s;
		tipo=tipoCaracter(s.charAt(s.length()-1));
	}
	/**
	 * Identifica el tipo de caracter de entrada
	 * @param token
	 * @return tipo caracter
	 */
	public static int tipoCaracter(char c) {	
		if(c>='0'&&c<='9')
			return NUM;
		if(c=='(')
			return PAR_IZ;
		if(c==')')
			return PAR_DER;
		if(c=='x')
			return VAR;
		if((c=='+')||(c=='-')||(c=='*')||(c=='/')||(c=='^'))
			return OPER;
		return -1;
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
