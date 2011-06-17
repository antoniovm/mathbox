package fp.excepciones;

public class ParentesisDesbalanceadosExcepcion extends Exception {

	public ParentesisDesbalanceadosExcepcion() {
		super("Error sintactico, Parentesis desbalanceados");
	}

	public ParentesisDesbalanceadosExcepcion(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParentesisDesbalanceadosExcepcion(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParentesisDesbalanceadosExcepcion(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
