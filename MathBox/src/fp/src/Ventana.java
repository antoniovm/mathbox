package fp.src;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Ventana extends JFrame implements  ActionListener,ChangeListener,MouseMotionListener,MouseListener{
	private Grafica grafica;
	private Parametros parametros;
	
	boolean pulsado;
	
	
	public Ventana() {
		setTitle("Function Plotter");
		
		Expresion e=new Expresion();
		parametros=new Parametros(e);
		grafica=new Grafica(new Funcion(e),20,20);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.weightx=1;
		gbc.weighty=1;
		
		
		add(grafica,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.weightx=1;
		gbc.weighty=0;
		
		add(parametros,gbc);
			
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		parametros.getDeslizador().addChangeListener(this);
		parametros.getTfExpresion().addActionListener(this);
		grafica.addMouseListener(this);
		grafica.addMouseMotionListener(this);
		
		
		
	}




	@Override
	public void stateChanged(ChangeEvent e) {
		grafica.setEscalaX(parametros.getDeslizador().getValue());
		grafica.setEscalaY(parametros.getDeslizador().getValue());
		grafica.repaint();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		parametros.getExpresion().postFija(parametros.getTfExpresion().getText());
		grafica.repaint();
		
	}




	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseClicked(MouseEvent e) {
		pulsado=true;
		
	}




	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mousePressed(MouseEvent e) {
		/*grafica.setOrigenX(grafica.getOrigenX()+e.getX());
		grafica.setOrigenY(grafica.getOrigenY()+e.getY());
		grafica.repaint();*/
		
	}




	@Override
	public void mouseReleased(MouseEvent e) {
		pulsado=false;
		
	}
	
	
}
