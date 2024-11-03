package juego;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.awt.Image;
import java.util.Random;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Image fondo;
    private Image imagenIslaNivel1;  // Imagenes de las islas
    private Image imagenIslaNivel2;  
    private Image imagenIslaNivel3;  
    private Image imagenIslaNivel4;  
    private Image imagenIslaNivel5;  
    Image ganaste;
    Image perdiste;
    private Isla[] islas;
    private Casa casa;
    private Pep pep;
    private Tortuga[] tortugas;
    private Gnomo[] gnomo;
    private Random random;
    private Isla islacasa;
    private Disparo[] disparos;
    private int maxDisparos = 100;
    private int eliminadas;
    private int salvados;
    private int perdidos;
    private boolean perder, ganar;
    Vidas[] vidas;
    private void verificarColisiones() {
        // Recorrer todos los disparos
        for (int i = 0; i < disparos.length; i++) {
            Disparo disparo = disparos[i];
            if (disparo != null) { // Asegura que el disparo no sea null
                // Recorrer todas las tortugas
                for (int j = 0; j < tortugas.length; j++) {
                    Tortuga tortuga = tortugas[j];
                    if (tortuga != null && disparo.colisionaCon(tortuga)) { 
                        // Si el disparo colisiona con la tortuga
                        tortugas[j] = null;  // La tortuga se elimina (se asigna a null)
                        disparos[i] = null;  // El disparo también se elimina
                        eliminadas+= 1;
                        break;  // Salir del bucle interno
                    }
                }
            }
        }
    }
    Juego() {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.random = new Random();
        this.disparos = new Disparo[maxDisparos];
        vidas= new Vidas[6];
        perder=false;
        ganar=false;
        

        // Cargar las imágenes de fondo y de las islas
        fondo = Herramientas.cargarImagen("Fondo.jpg");
        imagenIslaNivel1 = Herramientas.cargarImagen("IslaNivel1.png"); 
        imagenIslaNivel2 = Herramientas.cargarImagen("IslaNivel2.png"); 
        imagenIslaNivel3 = Herramientas.cargarImagen("IslaNivel34.png");  
        imagenIslaNivel4 = Herramientas.cargarImagen("IslaNivel34.png"); 
        imagenIslaNivel5 = Herramientas.cargarImagen("IslaNivel5.png"); 
        ganaste = Herramientas.cargarImagen("ganaste.jpg");
        perdiste = Herramientas.cargarImagen("gameover.png");
        // Inicializar las islas del nivel 1 con la imagen
        this.islas = new Isla[20];
        int anchoEntorno = 800;
        int altoEntorno = 600;

        // Nivel 1 (más bajo) - 5 islas
        double alturaNivel1 = altoEntorno - 100;
        for (int i = 0; i < 5; i++) {
            double x = (anchoEntorno / 6) * (i + 1);
            int anchoIsla = 90;
            islas[i] = new Isla(x, alturaNivel1, anchoIsla, 20, imagenIslaNivel1); 
        }

        // Nivel 2 - 4 islas
        double alturaNivel2 = altoEntorno - 200;
        for (int i = 0; i < 4; i++) {
            double x = (anchoEntorno / 5) * (i + 1);
            int anchoIsla = 120;
            islas[i + 5] = new Isla(x, alturaNivel2, anchoIsla, 20, imagenIslaNivel2);  
        }

        // Nivel 3 - 3 islas
        double alturaNivel3 = altoEntorno - 300;
        for (int i = 0; i < 3; i++) {
            double x = (anchoEntorno / 4) * (i + 1);
            int anchoIsla = 150;
            islas[i + 9] = new Isla(x, alturaNivel3, anchoIsla, 20, imagenIslaNivel3);  
        }

        // Nivel 4 - 2 islas
        double alturaNivel4 = altoEntorno - 400;
        for (int i = 0; i < 2; i++) {
            double x = (anchoEntorno / 3) * (i + 1);
            int anchoIsla = 150;
            islas[i + 12] = new Isla(x, alturaNivel4, anchoIsla, 20, imagenIslaNivel4);
        }

        // Nivel 5 (superior) - 1 isla
        double alturaNivel5 = altoEntorno - 500;
        islas[14] = new Isla(anchoEntorno / 2, alturaNivel5, 100, 20, imagenIslaNivel5);  

        // Crear la casa en la isla más alta
        Isla islaAlta = islas[14];
        this.casa = new Casa(islaAlta.getX(), islaAlta.getY() - 30, 40, 30);
                                     
        this.pep = new Pep(anchoEntorno / 2, alturaNivel1 - 30);
        this.tortugas = new Tortuga[3];
        for (int i = 0; i < tortugas.length; i++) {
            double posicionX = random.nextInt(entorno.ancho()); // Posición aleatoria en el eje X
            tortugas[i] = new Tortuga(posicionX, 0); // Iniciar todas las tortugas desde Y=0
        }
      
        this.gnomo= new Gnomo[4];
        
        for (int i=0;i <4;i++) {
        	gnomo[i] = new Gnomo(395,50);
        }
        this.entorno.iniciar();
    }
    

    public void tick() {
        entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
        entorno.cambiarFont("Arial Black", 18, Color.white);
		entorno.escribirTexto("Tortugas eliminadas: " + eliminadas, 30, 550);
		entorno.escribirTexto("Gnomos salvados: " + salvados, 30, 570);
		entorno.escribirTexto("Gnomos perdidos: " + perdidos, 30, 590);
		entorno.escribirTexto("Tiempo transcurrido: " + entorno.tiempo()/1000, 450, 550);
        // Dibujar las islas
        for (Isla isla : islas) {
            if (isla != null) {
                isla.dibujarse(entorno);
            }
        }
        // Dibujar y mover cada tortuga
        for (Tortuga tortuga : tortugas) {
            if (tortuga != null) { 
                tortuga.dibujarse(entorno);
                tortuga.moverse(islas, islacasa); 
            }                 
        }
        
        casa.dibujarse(entorno);
        pep.moverse(entorno, islas);
        pep.dibujarse(entorno);
        
        for (int i = 0; i < 4; i++) {
			gnomo[i].dibujarse(entorno);
			gnomo[i].moverse(islas);
            if(gnomo[i].getY()>600) {
            	perdidos += 1;
        	    gnomo[i].respawn(395, 50);        	   
           }
           if (pep.pepColisionaConGnomo(gnomo[i].getX(), gnomo[i].getY(), gnomo[i].getAncho(), gnomo[i].getAlto())&& pep.getY()>300) {        	   
        	   gnomo[i].respawn(395, 50); //si colisiona lo salva y vuelve a respawnear en la coordenada de la casa
        	   salvados+= 1;        	   
           }
           if(salvados >= 10) {
        	   ganar = true;   			
           }
           if(perdidos >= 10) {
        	   perder = true;
           }
        }
        if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
            for (int i = 0; i < maxDisparos; i++) {
                if (disparos[i] == null) {  // Busca una posición libre en el arreglo
                    disparos[i] = pep.disparar(); // Asigna un nuevo disparo en esa posición
                    break;
                }
            }
        }
        for (int i = 0; i < maxDisparos; i++) {
            if (disparos[i] != null) {
                disparos[i].mover();
                disparos[i].dibujar(entorno);

                // Elimina el disparo si sale de la pantalla
                if (disparos[i].getX() < 0 || disparos[i].getX() > entorno.ancho()) {
                    disparos[i] = null; 
                }
            }
        }
        if (pep.getY() > 600) {
            pep = null;
            perder = true;
        }
        boolean aux=false;
		for(int i = 0; i < vidas.length ; i ++) {
			if(pep==null) {
				vidas[i]=null;
			}
			if(pep!=null && vidas[i]==null && pep.cantVidas!=0) {			
	        	vidas[i]= new Vidas((entorno.ancho()-30) -(40*i),30);        	
	        	pep.cantVidas--;
			}
			if(vidas[i]!=null) {
			vidas[i].mostrar(entorno);
			//aux va a ser true siempre que al menos haya 1 vida 
			aux=true;
			}
		}
		if(ganar==true) {
			entorno.dibujarImagen(ganaste, 400, 300, 0,2);
		}
		if(perder==true) {
			entorno.dibujarImagen(perdiste, 400, 300, 0,0.5);
		}
        verificarColisiones();
    }
    
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}