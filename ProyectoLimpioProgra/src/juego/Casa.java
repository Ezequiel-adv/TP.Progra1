package juego;

import entorno.Entorno;
import java.awt.Color;

public class Casa {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    public Casa(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;  
        this.alto = alto;    
    }
    
    public void dibujarse(Entorno entorno) {
        Color colorCasa = new Color(150, 75, 0); // Color marrón 
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, colorCasa); // 0 es el ángulo de rotación
    }

    // Getters para obtener la posición de la casa
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
