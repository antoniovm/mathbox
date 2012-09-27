package fp.excepciones;

public class ValorNoSoportadoException extends Exception {

	public ValorNoSoportadoException() {
		super("Valor no seportado para operar matematicamente");
	}

	public ValorNoSoportadoException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ValorNoSoportadoException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ValorNoSoportadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
}
