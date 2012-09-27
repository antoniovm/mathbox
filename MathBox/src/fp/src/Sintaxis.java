package fp.src;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

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
		StringTokenizer strtok=new StringTokenizer(sb+"","+*/()^",true);	//Division en tokens
		
		
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
		//-233.44378745
		return Pattern.matches("-??[0-9]+(.[0-9]+)", token);
		
	}
	/**
	 * Comprueba si la constante es correcta
	 * @param token
	 * @return	true si es correco, false en caso contrario
	 */
	public static boolean comprobarConstante(String token) {
		
		return Pattern.matches("-??(e|pi)", token);
		
		
	}
	/**
	 * Comprueba si la aplicacion es correcta
	 * @param token
	 * @return true si es correco, false en caso contrario
	 */
	public static boolean comprobarAplicacion(String token) {
		
		return Pattern.matches("-??(log|ln|sen|sec|cos|cosec|cotan|tan)", token);

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
			//Despues de un signo, solo puede ir un numero, un parentesis izquierdo o una alpicacion
			case MathChar.SIGNO:return ((tipoActualCadena==MathChar.NUM)||(tipoActualCadena==MathChar.CONST)||(tipoActualCadena==MathChar.VAR)||(tipoActualCadena==MathChar.PAR_IZ)||(tipoActualCadena==MathChar.AP));
			//Despues de un operador, solo puede ir un numero, una variable, una constante, un parentesis izquierdo o una aplicacion
			case MathChar.OPER:return ((tipoActualCadena==MathChar.NUM)||(tipoActualCadena==MathChar.VAR)||(tipoActualCadena==MathChar.CONST)||(tipoActualCadena==MathChar.PAR_IZ)||(tipoActualCadena==MathChar.AP));
			//Despues de un parentesis izquierdo, solo puede ir un signo, un numero, una variable, una constante o una aplicacion
			case MathChar.PAR_IZ:return ((tipoActualCadena==MathChar.SIGNO)||(tipoActualCadena==MathChar.NUM)||(tipoActualCadena==MathChar.VAR)||(tipoActualCadena==MathChar.CONST)||(tipoActualCadena==MathChar.PAR_IZ)||(tipoActualCadena==MathChar.AP));
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
		
		
		for (int i=0; i < s.length(); i++) {	//Recorrer la cadena entera
			if(s.charAt(i)=='-'){				//El algoritmo transforma la diferencia en una suma negativa
				signosConcatenados(s, i);
				i++;							//Añade un caracter mas (+)
			}
			if(s.charAt(i)=='+'){
				signosConcatenados(s, i);		//No añade nada
			}
		}
		if(s.charAt(0)=='+') {//Si el primer signo es un signo 
			s.deleteCharAt(0);	//borrar signo
		}

	}
	/**
	 * Comprueba y simplifica los signos concatenados
	 * @param s
	 * @param j
	 */
	private static void signosConcatenados(StringBuffer s, int j) {
		boolean signosMenosImpar=false;
		
		for (int i = j; i < s.length(); ) {	//Contar signos
			
			
			if(s.charAt(i)=='-'){			//Signo -
				signosMenosImpar=!signosMenosImpar;	//Numero par/impar de signos -
				s.deleteCharAt(i);			//Eliminar signo
				continue;
			}
			
			
			if(s.charAt(i)=='+'){	//Signo +
				s.deleteCharAt(i);	//Eliminar signo
				continue;
			}
			
			
			if((s.charAt(j)!='-')&&(s.charAt(j)!='+')){ //si el siguiente carcater no es + ni -
				if(signosMenosImpar){ 	
					s.insert(j, '-');
				}
				s.insert(j, '+');	//Poner el signo definitivo
				break;
			}
			
		}
				
		
		
	
	}
}
