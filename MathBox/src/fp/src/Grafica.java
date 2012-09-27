package fp.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import fp.excepciones.ErrorSintacticoExcepcion;
import fp.excepciones.ValorNoSoportadoException;

public class Grafica extends JPanel{
	private int origenX, origenY;
	private double x, y; 
	private double escalaX, escalaY;
	private Funcion f;
	
	public Grafica() {
		x=y=0;
		setSize(500,500);
		origenX=getWidth()/2;
		origenY=getHeight()/2;
		escalaX=10;
		escalaY=10;
		f=new Funcion();
		setBackground(Color.white);
		
	}
	public Grafica(Funcion f,double escalaX,double escalaY) {
		x=y=0;
		setSize(500,500);
		origenX=getWidth()/2;
		origenY=getHeight()/2;
		this.escalaX=escalaX+10;
		this.escalaY=escalaY+10;
		this.f=f;
		
	}
	
	@Override
	public void paint(Graphics gr) {
		super.paint(gr);
		Graphics2D g = (Graphics2D)gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	//Antialiasing
		eje(g);
		pintarFuncion(g);
		
	}

	private void pintarFuncion(Graphics g) {
		if(!f.valida())
			return;
		MathChar mX = new MathChar();
		MathChar mX2 = new MathChar();
		
		g.setColor(Color.black);
		int y = 0,y2 = 0,x,x2; 
		for (x = origenX-getWidth(); x < getWidth()-origenX; x++) {
			mX.setCaracter((double)x/(escalaX)*(escalaY)+"");
			
			
			try {
				y=(int)(-f.f(mX));
			} catch (ErrorSintacticoExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ValorNoSoportadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x2=x+1;
			
			mX2.setCaracter((double)x2/(escalaX)*(escalaY)+"");
			try {
				y2=(int)(-f.f(mX2));
			} catch (ErrorSintacticoExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ValorNoSoportadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawLine(x+origenX, y+origenY, x2+origenX, y2+origenY);
		}
		
	}

	private void eje(Graphics g) {
		refrescarEje();
		// X
		for (int x = (int)((origenX-getWidth())/10*(escalaX)); x < getWidth()-origenX; x+=(escalaX)) {
			g.setColor(new Color(220, 220, 220));
			g.drawLine(x+origenX, 0, x+origenX, getHeight());	//Cuadricula X
			g.setColor(Color.gray);
			g.drawLine(x+origenX, origenY-2, x+origenX, origenY+2); //Rayitas X
			g.drawString((int)((x)/(escalaX))+"", x+origenX+2, origenY+15);	//Numerito X
		}
		// Y
		for (int y = (int)((origenY-getHeight())/10*(escalaY)); y < getHeight()-origenY; y+=(escalaY)) {
			g.setColor(new Color(220, 220, 220));
			g.drawLine(0, y+origenY, getWidth(), y+origenY); //Cuadricula Y
			g.setColor(Color.gray);
			g.drawLine(origenX-2, y+origenY, origenX+2, y+origenY); //Rayitas Y
			g.drawString((int)((-y)/(escalaY))+"", origenX+2 , y+origenY+15);	//Numerito Y
		}
		
		g.drawLine(0, origenY, getWidth(), origenY);	//Eje x
		g.drawLine(origenX, 0, origenX, getHeight());	//Eje y
		
	}

	private void refrescarEje() {
		origenX=(getWidth()/2);
		origenY=(getHeight()/2);
		
	}
	public int getOrigenX() {
		return origenX;
	}
	public void setOrigenX(int origenX) {
		this.origenX = origenX;
	}
	public int getOrigenY() {
		return origenY;
	}
	public void setOrigenY(int origenY) {
		this.origenY = origenY;
	}
	public double getEquis() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getYGriega() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getEscalaX() {
		return escalaX;
	}
	public void setEscalaX(double escalaX) {
		this.escalaX = escalaX;
	}
	public double getEscalaY() {
		return escalaY;
	}
	public void setEscalaY(double escalaY) {
		this.escalaY = escalaY;
	}
	public Funcion getF() {
		return f;
	}
	public void setF(Funcion f) {
		this.f = f;
	}
	
}
