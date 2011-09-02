package fp.src;

import java.util.StringTokenizer;
import fp.excepciones.ErrorSintacticoExcepcion;
import fp.excepciones.OperadorIncorrectoExcepcion;
import fp.excepciones.ParentesisDesbalanceadosExcepcion;

public class Sintaxis {

	
	/**
	 * Analiza la expresion infija en busca de errores
	 * @throws ErrorSintacticoExcepcion 
	 * 
	 */
	public static String analizar(String s) throws ParentesisDesbalanceadosExcepcion, OperadorIncorrectoExcepcion, ErrorSintacticoExcepcion{
		int ultimoTipoCadena=0;		//Variable para la coherencia en el orden sintactico de los tipos de cadenas
		String token;
		StringBuffer sb=new StringBuffer(s);	//Variable para la modificacion de los caracteres de la cadena
		int parentesis=0;

		simplificarSignos(sb);	//Se simplifican todos los signos de la cadena
		StringTokenizer strtok=new StringTokenizer(sb+"","+-*/()^",true);	//Division en tokens
		
		token=strtok.nextToken();
		if(MathChar.tipoCaracter(token)==MathChar.PAR_IZ) parentesis++;	//Cuenta de los parentesis
		
		ultimoTipoCadena=MathChar.tipoCaracter(token);	//Cuenta del ultimo tipo de cadena usado
		
		while(strtok.hasMoreTokens()){
					
			token=strtok.nextToken();	//Siguiente token a analizar
			
			if(MathChar.tipoCaracter(token)==MathChar.PAR_IZ) parentesis++;	//Cuenta de los parentesis
			if(MathChar.tipoCaracter(token)==MathChar.PAR_DER) parentesis--;
			
			if(!siguienteTipoValido(ultimoTipoCadena, MathChar.tipoCaracter(token)))
				throw new ErrorSintacticoExcepcion();	//Comprueba la validez de los tipos de tokens
			
			ultimoTipoCadena=MathChar.tipoCaracter(token);	//Cuenta del ultimo tipo de cadena usado
			
			
		}
		
		/*
		 * Contemplar que haya un signo tras un parentesis izquierdo (añadir un 0 entre el parentesis y el signo)
		 */
		
		if(parentesis!=0)
			throw new ParentesisDesbalanceadosExcepcion();
		return sb+"";
		
	}
	/**
	 * Escanea la cadena para comprobar si es un número en su totalidad
	 * @param token
	 * @return true si es correco, false en caso contrario
	 */
	public static boolean comprobarNumero(String token) {
		for (int i = 0; i < token.length(); i++) {
			if(!(token.charAt(i)>='0'&&token.charAt(i)<='9'))	//Si se sale de este rango ascii, no es un numero
				return false;
		}
		return true;

	}
	/**
	 * Comprueba si la constante es correcta
	 * @param token
	 * @return	true si es correco, false en caso contrario
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
		return false;
	}
	/**
	 * Comprueba si la aplicacion es correcta
	 * @param token
	 * @return true si es correco, false en caso contrario
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
	/**
	 * Establece un orden entre los tipos de tokens
	 * @param ultimoTipoCadena
	 * @param tipoActualCadena
	 * @return true si es correco, false en caso contrario
	 */
	private static boolean siguienteTipoValido(int ultimoTipoCadena, int tipoActualCadena) {
		
		
		switch (ultimoTipoCadena) {
			//Despues de un numero, solo puede ir un signo, un operador o un parentesis derecho
			case MathChar.NUM:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.OPER)||(tipoActualCadena==MathChar.PAR_DER));
			//Despues de un signo, solo puede ir otro signo, un numero, un parentesis izquierdo o una alpicacion
			case MathChar.SIGNO:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.NUM)||(tipoActualCadena==MathChar.CONST)||(tipoActualCadena==MathChar.VAR)||(tipoActualCadena==MathChar.PAR_IZ)||(tipoActualCadena==MathChar.AP));
			//Despues de un operador, solo puede ir un numero, una variable, una constante, un parentesis izquierdo o una aplicacion
			case MathChar.OPER:return ((tipoActualCadena==MathChar.NUM)||(tipoActualCadena==MathChar.VAR)||(tipoActualCadena==MathChar.CONST)||(tipoActualCadena==MathChar.PAR_IZ)||(tipoActualCadena==MathChar.AP));
			//Despues de un parentesis izquierdo, solo puede ir un signo, un numero, una variable o una constante
			case MathChar.PAR_IZ:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.NUM)||(tipoActualCadena==MathChar.VAR)||(tipoActualCadena==MathChar.CONST)||(tipoActualCadena==MathChar.PAR_IZ));
			//Despues de un parentesis derecho, solo puede ir un signo o un operador
			case MathChar.PAR_DER:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.OPER)||(tipoActualCadena==MathChar.PAR_DER));
			//Despues de una variable, solo puede ir un signo, un operador o un parentesis derecho
			case MathChar.VAR:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.OPER)||(tipoActualCadena==MathChar.PAR_DER));
			//Despues de una constante, solo puede ir un signo, un operador o un parentesis derecho
			case MathChar.CONST:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.OPER)||(tipoActualCadena==MathChar.PAR_DER));
			//Despues de una aplicacion, solo puede ir un parentesis izquierdo
			case MathChar.AP:return ((tipoActualCadena==MathChar.PAR_IZ));
	
			default:break;
		
		}
		return false;
	}
	/**
	 * Simplifica y prepara los signos de la cadena entrante para el procesado a postfija
	 * @param s
	 */
	
	private static void simplificarSignos(StringBuffer s) {
		int i=0;
		while(s.charAt(i)=='+') {//Si el primer signo es un signo 
			s.deleteCharAt(i);	//borrar signo
		}
		if(s.charAt(i)=='-') {//Si el primer signo es un signo 
			s.insert(0, '0');
			i++;
		}
		for (; i < s.length(); i++) {
			if((s.charAt(i)=='-')||(s.charAt(i)=='+')){
				if((s.charAt(i-1)=='(')){	//Si hay un signo tras un parentesis, insertamos un cero entre ellos
					s.insert(i, '0');
					i++;
				}
				signosConcatenados(s, i);
			}
		}

	}
	/**
	 * Comprueba y simplifica los signos concatenados
	 * @param s
	 * @param j
	 */
	private static void signosConcatenados(StringBuffer s, int j) {
		int signosMenos=0;
		char signoFinal;
		
		for (int i = j; i < s.length(); i++) {	//Contar signos
			if(s.charAt(i)=='-'){	//Signo -
				signosMenos++;
				continue;
			}
			if(s.charAt(i)=='+'){	//Signo +
				continue;
			}
			break;
			
		}
		
		
		if(signosMenos%2==1)signoFinal='-';	//Numero impar de signos -
		else signoFinal='+';				//Numero par de signos -
		
		
		for (int i = j; i < s.length()-1; ) {
			if((s.charAt(j+1)!='-')&&(s.charAt(j+1)!='+')){ //si el siguiente carcater no es + ni -
				s.setCharAt(j, signoFinal);	//Poner el signo definitivo
				break;
			}
			s.deleteCharAt(j);	//borrar signo
		}
		
	
	}
}
