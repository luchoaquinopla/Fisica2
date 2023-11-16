
    package com.fisica;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
public class SimulacionOndasElectromagneticas extends JFrame {

    private static final int ANCHO = 800;
    private static final int ALTO = 600;

    private BufferedImage buffer;
    private double[][] onda;
    private double tiempo = 0;

    public SimulacionOndasElectromagneticas() {
        setTitle("Simulación de Ondas Electromagnéticas");
        setSize(ANCHO, ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
        onda = new double[ANCHO][ALTO];

        // Inicializar onda con un nuevo patrón
        for (int x = 0; x < ANCHO; x++) {
            for (int y = 0; y < ALTO; y++) {
                onda[x][y] = Math.sin(x * 0.1) * Math.cos(y * 0.1 + tiempo);
            }
        }

        // Utilizar SwingWorker para la simulación en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Bucle de simulación
                while (true) {
                    actualizarOnda();
                    dibujarOnda();
                    repaint();

                    try {
                        Thread.sleep(10); // Controla la velocidad de la simulación
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        worker.execute(); // Iniciar el SwingWorker
    }

    private void actualizarOnda() {
        tiempo += 0.1;

        // Actualizar la onda en función del tiempo con un nuevo patrón
        for (int x = 0; x < ANCHO; x++) {
            for (int y = 0; y < ALTO; y++) {
                onda[x][y] = Math.sin(x * 0.1) * Math.cos(y * 0.1 + tiempo);
            }
        }
    }

    private void dibujarOnda() {
        for (int x = 0; x < ANCHO; x++) {
            for (int y = 0; y < ALTO; y++) {
                // Mapear a colores de manera diferente
                int red = (int) ((onda[x][y] + 1) * 127.5);
                int green = (int) ((Math.cos(x * 0.05) + 1) * 127.5);
                int blue = (int) ((Math.sin(y * 0.05) + 1) * 127.5);
                
                buffer.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulacionOndasElectromagneticas().setVisible(true));
    }
}

/*public class SimulacionOndasElectromagneticas extends JFrame {

    private static final int ANCHO = 800;
    private static final int ALTO = 600;

    private BufferedImage buffer;
    private double[][] onda;
    private double tiempo = 0;

    public SimulacionOndasElectromagneticas() {
        setTitle("Simulación de Ondas Electromagnéticas");
        setSize(ANCHO, ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
        onda = new double[ANCHO][ALTO];

        // Inicializar onda (simulación de una onda inicial)
        for (int x = 0; x < ANCHO; x++) {
            for (int y = 0; y < ALTO; y++) {
                onda[x][y] = Math.sin(x * 0.1) + Math.sin(y * 0.1);

            }
        }

        // Utilizar SwingWorker para la simulación en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Bucle de simulación
                while (true) {
                    actualizarOnda();
                    dibujarOnda();
                    repaint();

                    try {
                        Thread.sleep(50); // Controla la velocidad de la simulación
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        worker.execute(); // Iniciar el SwingWorker
    }

    private void actualizarOnda() {
        tiempo += 0.1;

        // Actualizar la onda en función del tiempo
        for (int x = 0; x < ANCHO; x++) {
            for (int y = 0; y < ALTO; y++) {
                onda[x][y] = Math.sin(x * 0.1 + tiempo) * Math.sin(y * 0.1 + tiempo);
            }
        }
    }

    private void dibujarOnda() {
        for (int x = 0; x < ANCHO; x++) {
            for (int y = 0; y < ALTO; y++) {
                int color = (int) ((onda[x][y] + 1) * 127.5);
                buffer.setRGB(x, y, new Color(color, color, color).getRGB());
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulacionOndasElectromagneticas().setVisible(true));
    }

}
/*Creación de la ventana: Se crea una ventana Swing con un título "Simulación de Ondas Electromagnéticas" y dimensiones de 800x600 píxeles.

Inicialización de variables: Se crea una imagen de búfer (buffer) para almacenar los píxeles de la simulación y una matriz bidimensional (onda) para representar la onda electromagnética en cada punto de la pantalla. El tiempo (tiempo) se inicializa a cero.

Inicialización de la onda: La matriz onda se inicializa con valores basados en funciones seno.

SwingWorker para la simulación en segundo plano: Se utiliza un SwingWorker para realizar la simulación en segundo plano y evitar que la interfaz gráfica se congele. En el método doInBackground, se actualiza continuamente la onda, se dibuja en el búfer y se repinta la interfaz gráfica cada 50 milisegundos.

Método actualizarOnda: Este método actualiza la matriz onda en función del tiempo. En este caso, la onda se actualiza utilizando funciones seno que dependen de las coordenadas x e y, así como del tiempo.

Método dibujarOnda: Este método convierte los valores de la matriz onda en colores y los asigna a la imagen de búfer (buffer). En este caso, los valores de la onda se mapean a niveles de gris.

Método paint: Se sobrescribe el método paint para dibujar la imagen de búfer en la ventana.

Método main: Se inicia la aplicación Swing invocando el constructor de SimulacionOndasElectromagneticas en el hilo de despacho de eventos de Swing.

En resumen, el programa crea una animación continua de ondas electromagnéticas que cambian con el tiempo y las muestra en una ventana gráfica. La velocidad de la simulación está controlada por el tiempo de espera en el bucle de simulación. 

Este tipo de función senoidal crea patrones que se asemejan a ondas que se propagan en forma de ondas estacionarias, y es por eso que se ve como un patrón de interferencia similar a un tablero de ajedrez.*/

