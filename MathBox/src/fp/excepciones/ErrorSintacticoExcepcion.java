package fp.excepciones;

public class ErrorSintacticoExcepcion extends Exception {

	public ErrorSintacticoExcepcion() {
		super("Error de sintaxis");
	}

	public ErrorSintacticoExcepcion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErrorSintacticoExcepcion(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorSintacticoExcepcion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
