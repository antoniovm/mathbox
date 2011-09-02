package fp.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

import fp.excepciones.OperadorIncorrectoExcepcion;
import fp.excepciones.ParentesisDesbalanceadosExcepcion;

public class Expresion {
	public Expresion() {
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
	private Queue<MathChar> salida;
	private Stack<Character> operadores;
	
	
	/**
	 * Transforma una expresion infija, en una postfija
	 * 
	 */
	public void postFija(String infija) {
		StringTokenizer strtok=new StringTokenizer(infija,"+-*/()^",true);
		boolean error=false;
		String token;
		
		salida.clear();
		
		while(strtok.hasMoreTokens()&&!error){
			
			token=strtok.nextToken();
			switch (MathChar.tipoCaracter(token)) {
				case MathChar.NUM:salida.add(new MathChar(token));break;
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
	/**
	 * Comprueba que la sintaxis es correcta
	 * @param s
	 * @return
	 * @throws ParentesisDesbalanceadosExcepcion
	 * @throws OperadorIncorrectoExcepcion 
	 */
	public boolean sintaxis(String s) throws ParentesisDesbalanceadosExcepcion, OperadorIncorrectoExcepcion{
		StringTokenizer strtok=new StringTokenizer(s,"+-*/()^",true);
		String token;
		StringBuffer sb=new StringBuffer(s);
		int parentesis=0, indice=0;
		
		while(strtok.hasMoreTokens()){
					
					token=strtok.nextToken();
					switch (MathChar.tipoCaracter(token)) {
						case MathChar.NUM:break;	//Numero, correcto
						case MathChar.SIGNO: signosConcatenados(sb, indice);break;
						case MathChar.OPER:nuevoOperador(token.charAt(0));break;
						case MathChar.PAR_IZ:operadores.push(token.charAt(0));break;
						case MathChar.PAR_DER:parentesisDerecho(token.charAt(0));break;
						case MathChar.VAR:salida.add(new MathChar(token));break;
						case MathChar.CONST:salida.add(new MathChar(token));break;
		
					default:System.err.println("Error de sintaxis");break;
					}
					indice+=token.length();
					
				}
		
		
		int i = 0;
		if(MathChar.tipoCaracter(s)==MathChar.OPER){//Si es un signo -
			if(s.charAt(i)=='-'){
				sb.append('0');	//Añadimos un 0 para hacer consistente la expresion
				sb.append(s.charAt(i));
			}else
			throw new OperadorIncorrectoExcepcion();
		}
			
		
		for (; i < s.length(); i++) {
			if(s.charAt(i)=='('){
				parentesis++;
				
			}
			if(s.charAt(i)==')')
				parentesis--;
			
		}
		
		if(parentesis!=0)
			throw new ParentesisDesbalanceadosExcepcion();
		return true;
		
	}
	
	private void signosConcatenados(StringBuffer s, int j) {
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
		
		
		for (int i = j; i < s.length()-1; i++) {
			if((s.charAt(j+1)!='-')||(s.charAt(j+1)!='+')){ //si el siguiente carcater no es + ni -
				s.setCharAt(j, signoFinal);	//Poner el signo definitivo
				break;
			}
			s.deleteCharAt(j);	//borrar signo
		}
		
	
	}
}
