package juego;
import java.awt.Color;
import entorno.Entorno;

public class Tortuga {
    private double x, y;
    private double velocidadX = 1;
    private boolean cayendo = true;
	private boolean sobreIsla;

    public Tortuga(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void moverse(Isla[] islas, Isla islaCasa) {
        if (cayendo) {
            y += 3; // Velocidad de la caída de la tortuga

            // Verifica si la tortuga ha colisionado con alguna isla, excluyendo la isla de la casa
            for (Isla isla : islas) {
                if (isla != null && isla != islaCasa && isla.colisionaConTortuga(x, y, 30, 30)) {
                	if (isla.getY() <= 250)
                		continue;
                    cayendo = false;  
                    y = isla.getY() - isla.getAlto() / 2 - 15; // Ajusta la posición Y para que quede sobre la isla
                    break;
                }
            }
        } 
        else {
            // Movimiento horizontal en la isla
            x += velocidadX;
            // Verificar colisiones con los extremos de la isla, excluyendo la isla de la casa
            for (Isla isla : islas) {
                if (isla != null && isla != islaCasa && isla.colisionaConTortuga(x, y, 30, 30)) {
                    if (x + 30 > isla.getX() + isla.getAncho() / 2 || x - 30 < isla.getX() - isla.getAncho() / 2) {
                        // Si llega al borde, cambiar la dirección
                        velocidadX = -velocidadX;
                    }
                    // Asegurarse de que la tortuga no se salga de la isla
                    if (x < isla.getX() - isla.getAncho() / 2) {
                        x = isla.getX() - isla.getAncho() / 2; // Ajustar la posición
                    } 
                    else if (x > isla.getX() + isla.getAncho() / 2) {
                        x = isla.getX() + isla.getAncho() / 2; // Ajustar la posición
                    }
                }
            }
        }
    }
    public void dibujarse(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, 30, 30, 0, Color.green); 
      
        }

    public void resetearPosicion(double nuevaX) {
        this.x = nuevaX;
        this.y = 0;
        this.setSobreIsla(false);  // Aseguramos que vuelva a caer
	}

	public boolean isSobreIsla() {
		return sobreIsla;
	}

	public void setSobreIsla(boolean sobreIsla) {
		this.sobreIsla = sobreIsla;
	}
    public boolean tortugaColisionaConGnomo(double gnomoX, double gnomoY, double gnomoAncho, double gnomoAlto) {
        // Verifica si Pep y el Gnomo están en la misma zona (o casi) para considerarlo una "colisión"
        return gnomoY + gnomoAlto / 2 >= this.y - 25 && gnomoY - gnomoAlto / 2 <= this.y + 25 &&
               gnomoX + gnomoAncho / 2 >= this.x - 15 && gnomoX - gnomoAncho / 2 <= this.x + 15;
    }
    

	public double getY() {
		return y;
	}
    public double getX() {
        return x;
    }
}
