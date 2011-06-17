package fp.excepciones;

public class OperadorIncorrectoExcepcion extends Exception {

	public OperadorIncorrectoExcepcion() {
		super("Uso de operador incorrectamente");
	}

	public OperadorIncorrectoExcepcion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public OperadorIncorrectoExcepcion(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public OperadorIncorrectoExcepcion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
