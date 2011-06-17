package fp.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Parametros extends JPanel  {
	
	private JTextField tfExpresion;
	private JLabel	etiqueta;
	private Expresion expresion;
	private JSlider deslizador;
	private double escala;
	
	public Parametros(Expresion expresion) {
		etiqueta=new JLabel("y=");
		tfExpresion=new JTextField(20);
		deslizador=new JSlider(10,100);
		deslizador.setPaintTicks(true);
		this.expresion=expresion;
		

		
		add(etiqueta);
		add(tfExpresion);
		add(deslizador);
		
	}
	public JTextField getTfExpresion() {
		return tfExpresion;
	}
	public void setTfExpresion(JTextField tfExpresion) {
		this.tfExpresion = tfExpresion;
	}
	public JLabel getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(JLabel etiqueta) {
		this.etiqueta = etiqueta;
	}
	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
	public JSlider getDeslizador() {
		return deslizador;
	}
	public void setDeslizador(JSlider deslizador) {
		this.deslizador = deslizador;
	}
	public double getEscala() {
		return escala;
	}
	public void setEscala(double escala) {
		this.escala = escala;
	}
	
	

}
