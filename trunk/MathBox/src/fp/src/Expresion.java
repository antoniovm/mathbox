package fp.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

import fp.excepciones.ErrorSintacticoExcepcion;
import fp.excepciones.OperadorIncorrectoExcepcion;
import fp.excepciones.ParentesisDesbalanceadosExcepcion;

public class Expresion {
	
	private String cadena;
	public String getCadena() {
		return cadena;
	}
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}
	private Queue<MathChar> salida;
	private Stack<MathChar> operadores;
	
	
	public Expresion() {
		operadores=new Stack<MathChar>();
		salida=new LinkedList<MathChar>();
	}
	public Expresion(String cadena) {
		this.cadena=cadena;
		operadores=new Stack<MathChar>();
		salida=new LinkedList<MathChar>();
	}
	/**
	 * Cadena de salida
	 * @return
	 */
	public Queue<MathChar> getSalida() {
		return salida;
	}
	public void setSalida(Queue<MathChar> salida) {
		this.salida = salida;
	}
	public Stack<MathChar> getOperadores() {
		return operadores;
	}
	public void setOperadores(Stack<MathChar> operadores) {
		this.operadores = operadores;
	}
	
	
	
	/**
	 * Transforma una expresion infija, en una postfija
	 * 
	 */
	public void postFija() {
		StringTokenizer strtok=new StringTokenizer(cadena,"+*/()^",true);
		boolean error=false;
		String token;
		
		salida.clear();
		
		while(strtok.hasMoreTokens()&&!error){
			
			token=strtok.nextToken();
			switch (MathChar.tipoCaracter(token)) {
				case MathChar.NUM:salida.add(new MathChar(token));break;
				case MathChar.SIGNO:nuevoOperador(token.charAt(0));break;
				case MathChar.OPER:nuevoOperador(token.charAt(0));break;
				case MathChar.AP:nuevaAplicacion(token);break;
				case MathChar.PAR_IZ:operadores.push(new MathChar(token));break;
				case MathChar.PAR_DER:parentesisDerecho(token.charAt(0));break;
				case MathChar.VAR:salida.add(new MathChar(token));break;
				case MathChar.CONST:salida.add(new MathChar(token));break;

			default:System.err.println("Error de sintaxis");error=true;break;
			}
			
		}
		if(salida.isEmpty())
			error=true;
		vaciarOperadores();
		if(error)
			salida.clear();
	}
	
	
	/**
	 * Vacia la pila de operadores
	 */
	private void vaciarOperadores() {
		while(!operadores.empty())
			salida.add(new MathChar(operadores.pop().toString()));		
	}
	/**
	 * Lee los parentesis para darle prioridad de las operaciones de su interior
	 * @param c
	 */
	private void parentesisDerecho(char c ) {
		while (!operadores.empty()&&(!operadores.peek().equals("("))) //Vaciamos operadores hasta encontrar el parentesis izquierdo
			salida.add(operadores.pop());
		if(operadores.empty()){					//Si vaciamos del todo, hay un error
			System.err.println("Parentesis desbalanceados");
			salida.clear();
			return;
		}
		if(operadores.peek().equals("(")){		//Parentesis encontrado, vaciamos
			operadores.pop();
			if((!operadores.empty())&&(operadores.peek().getTipo()==MathChar.AP))	//Si el siguiente token es una aplicacion, vaciar
				salida.add(operadores.pop());
		}
		
	}
	private void nuevaAplicacion(String cadena) {
		operadores.add(new MathChar(cadena));

	}
	
	/**
	 * Introduce los operadores en la lista de salida segun su prioridad
	 * @param caracter
	 */
	private void nuevoOperador(char c) {
		while (!operadores.empty()&&(precedencia(operadores.peek().toString().charAt(0))>=precedencia(c))) 
			salida.add(new MathChar(operadores.pop().toString()));
		operadores.add(new MathChar(c+""));
	
		
	}
	/**
	 * Establece una preferencia entre operadores
	 * @param caracter
	 * @return prioridad
	 */
	private int precedencia(char c) {
		if(c=='^')
			return 2;
		if(c=='*'||c=='/')
			return 1;
		if(c=='+'||c=='-')
			return 0;
		return -1;
	}
	
	public double evaluar(double valor){
		Queue<MathChar> aux=new LinkedList<MathChar>();
		Stack<MathChar> pila=new Stack<MathChar>();	//Pila para la resolucion de operaciones anidadas
		
		
		double a,b;	//Pareja de operandos
		
		
		for (Iterator<MathChar> iterator = salida.iterator(); iterator.hasNext();) {	//Evaluar en x
			MathChar token =  iterator.next();
			if(token.equals("x"))
				aux.add(new MathChar(valor+""));
			else
				aux.add(new MathChar(token+""));
			
		}
		
		
		double solucion=0;
		if(aux.size()==1){		//Caso de ser una constante
			if(aux.peek().getTipo()!=MathChar.NUM){
				salida.clear();
				System.err.println("Error de sintaxis");
				return -1;
			}	
			
			solucion=Double.parseDouble(aux.poll().toString());
			return solucion;
		}
		
		for (Iterator<MathChar> iterator = aux.iterator(); iterator.hasNext();) {	//Recorre la cola en postfija
			MathChar temp=iterator.next();
			
			
			switch (temp.getTipo()) {
			case MathChar.NUM:
				pila.push(temp);				
				break;
			case MathChar.AP:
				pila.push(new MathChar(aplicar(temp,pila.pop())+""));			
				break;
			case MathChar.OPER:
			case MathChar.SIGNO:
				pila.push(new MathChar(operar(pila.pop(),pila.pop(),temp)+""));				
				break;
			case MathChar.CONST:
				pila.push(new MathChar(valorConstante(temp)+""));			
				break;

			default:
				break;
			}
			
		}
		
		
		return Double.parseDouble(pila.pop().toString());	//La solucion se encuentra en la primera posicion de la pila
	}
	/**
	 * Devuelve el valor numerico aproximado de las constantes
	 * @param temp
	 * @return
	 */
	private double valorConstante(MathChar constante) {
		boolean negativo=false;	//Contemplamos si la subcadena es negativa
		
		if(constante.toString().charAt(0)=='-'){
			constante.setCaracter(constante.toString().substring(1));
			negativo=true;
		}
		
		switch (constante.toString().charAt(0)) {
		case 'e':
			if(negativo)
				return -Math.E;
			return Math.E;
		case 'p':
			if (constante.equals("pi")){
				if(negativo)
					return -Math.PI;
				return Math.PI;
			}
			break;

		default:
			break;
		}
		
		return Double.MIN_VALUE;
		
	}
	/**
	 * Realiza la operacion indicada a los valores por parametro
	 * @param op2
	 * @param op1
	 * @param operador
	 * @return
	 */
	private double operar(MathChar op2, MathChar op1, MathChar operador) {
		double a = Double.parseDouble(op1.toString());
		double b = Double.parseDouble(op2.toString());
		
		switch (operador.toString().charAt(0)) {
		case '+': return(a+b);
		case '-': return(a-b);
		case '*': return(a*b);
		case '/': return(a/b);
		case '^': return Math.pow(a, b);
		
		default:	System.err.println("Error de sintaxis");
	
}
		return Double.MIN_VALUE;
	}
	/**
	 * Devuelve el resultado de la aplicacion indicada, al numero pasado por parametro
	 * @param aplicacion
	 * @param num
	 * @return
	 */
	private double aplicar(MathChar aplicacion, MathChar num) {
		boolean negativo=false;
		double parametro=Double.parseDouble(num.toString());
		double resultado=Double.MIN_VALUE;
		
		if(aplicacion.toString().charAt(0)=='-'){
			aplicacion.setCaracter(aplicacion.toString().substring(1));
			negativo=true;
		}
		
		switch (aplicacion.toString().charAt(0)) {
		case 'l':
			if (aplicacion.equals("log"))	//Logaritmo base 10
				resultado= Math.log10(parametro);
			if (aplicacion.equals("ln"))	//Logaritmo neperiano
				resultado= Math.log(parametro);
			break;
		case 's':
			if (aplicacion.equals("sen"))	//Seno
				resultado= Math.sin(parametro);
			if (aplicacion.equals("sec"))	//Secante
				resultado= 1/Math.cos(parametro);
			break;
		case 'c':
			if (aplicacion.equals("cos"))	//Coseno	
				resultado= Math.cos(parametro);
			if (aplicacion.equals("cosec"))	//Cosecante
				resultado= 1/Math.sin(parametro);
			if (aplicacion.equals("cotan"))	//Cotangente
				resultado= Math.tan(parametro);
			break;
		case 't':
			if (aplicacion.equals("tan"))	//Tangente
				resultado= Math.tan(parametro);
			break;

		default:
			break;

		}
		if(negativo)
			return -resultado;
		return resultado;
	}
	
	public void mostrarPostfija() {
		for (Iterator<MathChar> iterator = salida.iterator(); iterator.hasNext();) {
			System.out.print(iterator.next()+" ");
			
		}
		System.out.println("");

	}
	public void mostrarInfija() {
		System.out.println(cadena);

	}
	/**
	 * Comprueba que la sintaxis es correcta
	 * @param string
	 * @return
	 * @throws ErrorSintacticoExcepcion 
	 * @throws ParentesisDesbalanceadosExcepcion
	 * @throws OperadorIncorrectoExcepcion 
	 */
	public void analizarSintaxis() throws ParentesisDesbalanceadosExcepcion, OperadorIncorrectoExcepcion, ErrorSintacticoExcepcion {
		cadena=Sintaxis.analizar(cadena);

	}
	
}
