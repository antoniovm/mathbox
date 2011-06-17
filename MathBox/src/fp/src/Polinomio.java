package fp.src;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Polinomio {
	Set<Monomio> polinomio;
	
	public Polinomio() {
		
	}
	
	public  Polinomio(Set<Monomio> polinomio) {
		this.polinomio=polinomio;
	}
	
	public void suma(Monomio monomio) {
		for (Iterator<Monomio> iterator = polinomio.iterator(); iterator.hasNext();) {
			Monomio m =  iterator.next();
			//polinomio.
			
		}

	}

}
