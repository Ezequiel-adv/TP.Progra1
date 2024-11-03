package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

public class Gnomo {
	private double x, y;
    private double velocidadX = 1.2; // Velocidad horizontal del gnomo
    private double velocidadY = 4; // Velocidad de caída
    private boolean cayendo = true; // Comienza cayendo
    private Random random;
    private Isla islaActual; // Isla en la que el gnomo está actualmente
    private int direccion; // 1 para derecha, -1 para izquierda
    private double ancho;
    private double alto;
    

    public Gnomo(double x, double y) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.direccion = random.nextBoolean() ? 1 : -1; // Dirección inicial aleatoria
        this.alto=20;
        this.ancho=10;
    }
    

    public void moverse(Isla[] islas) {
        if (cayendo) {
            // Caída del gnomo
            y += velocidadY;

            // Verifica si el gnomo ha colisionado con alguna isla
            for (Isla isla : islas) {
                if (isla != null && isla.colisionaConGnomo(x, y, 20, 20)) {
                    cayendo = false; 
                    velocidadY = 0; // Detiene la velocidad de caída
                    y = isla.getY() - isla.getAlto() / 2 - 10; // Ajusta la posición Y para que quede sobre la isla
                    islaActual = isla; 
                    cambiarDireccionAleatoria(); 
                    break;
                }
            }
        } else {
            // Movimiento horizontal en la isla
            x += velocidadX * direccion;

            // Verificar si el gnomo se sale del borde de la isla actual
            if (islaActual != null) {
                double limiteIzquierdo = islaActual.getX() - islaActual.getAncho() / 1.63;
                double limiteDerecho = islaActual.getX() + islaActual.getAncho() / 1.63;

                // Iniciar caída sin cambiar dirección si alcanza el borde
                if (x < limiteIzquierdo || x > limiteDerecho) {
                    iniciarCaida();
                }
            }
        }
    }

    // Método para iniciar la caída
    private void iniciarCaida() {
        cayendo = true;
        velocidadY = 4; 
        islaActual = null; 
    }
    private void cambiarDireccionAleatoria() {
        direccion = random.nextBoolean() ? 1 : -1; // Asigna 1 para derecha o -1 para izquierda
    }
    public void respawn(int x, int y) {
        this.x = x;
        this.y = y;
    }  
                   	   	
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    public double getAlto() {
		
		return this.alto;
	}


	public double getAncho() {
		
		return this.ancho;
	}
    
    
    public void dibujarse(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, 10  , 20, 0, Color.RED); // Dibuja al gnomo
    }


	
}
