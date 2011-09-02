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
	private Stack<Character> operadores;
	
	
	public Expresion() {
		operadores=new Stack<Character>();
		salida=new LinkedList<MathChar>();
	}
	public Expresion(String cadena) {
		this.cadena=cadena;
		operadores=new Stack<Character>();
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
	public Stack<Character> getOperadores() {
		return operadores;
	}
	public void setOperadores(Stack<Character> operadores) {
		this.operadores = operadores;
	}
	
	
	
	/**
	 * Transforma una expresion infija, en una postfija
	 * 
	 */
	public void postFija() {
		StringTokenizer strtok=new StringTokenizer(cadena,"+-*/()^",true);
		boolean error=false;
		String token;
		
		salida.clear();
		
		while(strtok.hasMoreTokens()&&!error){
			
			token=strtok.nextToken();
			switch (MathChar.tipoCaracter(token)) {
				case MathChar.NUM:salida.add(new MathChar(token));break;
				case MathChar.SIGNO:nuevoOperador(token.charAt(0));break;
				case MathChar.OPER:nuevoOperador(token.charAt(0));break;
				case MathChar.PAR_IZ:operadores.push(token.charAt(0));break;
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
	private void vaciarOperadores() {
		while(!operadores.empty())
			salida.add(new MathChar(operadores.pop().toString()));		
	}
	/**
	 * Lee los parentesis para darle prioridad de las operaciones de su interior
	 * @param c
	 */
	private void parentesisDerecho(char c ) {
		while (!operadores.empty()&&(operadores.peek().charValue()!='(')) 
			salida.add(new MathChar(operadores.pop().toString()));
		if(operadores.empty()){
			System.err.println("Parentesis desbalanceados");
			salida.clear();
			return;
		}
		if(operadores.peek().charValue()=='(')
			operadores.pop();
		
	}
	/**
	 * Introduce los operadores en la lista de salida segun su prioridad
	 * @param caracter
	 */
	private void nuevoOperador(char c) {
		while (!operadores.empty()&&(precedencia(operadores.peek())>=precedencia(c))) 
			salida.add(new MathChar(operadores.pop().toString()));
		operadores.add(c);
	
		
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
		double a,b;
		for (Iterator iterator = salida.iterator(); iterator.hasNext();) {
			MathChar str = (MathChar) iterator.next();
			if(str.equals("x"))
				aux.add(new MathChar( valor+""));
			else
				aux.add(new MathChar(str+""));
			
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
			
		
		a=Double.parseDouble(aux.poll().toString());
		b=Double.parseDouble(aux.poll().toString());
		
		solucion=evaluar(a, b, aux);
		while(!aux.isEmpty()){	
			solucion=evaluar(solucion,Double.parseDouble(aux.poll().toString()),aux); //Buscamos la solucion de parentesis anidados recursivamente
		}
		return solucion;
	}
	private double evaluar(double  a , double b, Queue<MathChar> aux) {
		double c;
		
		if(aux.peek().getTipo()!=MathChar.OPER){
			c=Double.parseDouble(aux.poll().toString());
			b=evaluar(b, c, aux);
		}
			
		
		
			
			c=aux.poll().toString().charAt(0);
			
			switch ((char)c) {
				case '+': return(a+b);
				case '-': return(a-b);
				case '*': return(a*b);
				case '/': return(a/b);
				case '^': 
					double solucion=1;
					for (int i = 0; i < b; i++) {
						solucion*=a;
				} 
					return solucion;
		
		
				default:	System.err.println("Error de sintaxis");
			
		}
			return -1;
		
	}
	public void mostrarPostfija() {
		for (Iterator iterator = salida.iterator(); iterator.hasNext();) {
			System.out.print(iterator.next());
			
		}

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
