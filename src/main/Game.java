package main;

import entities.Player;

import java.awt.*;

public class Game implements Runnable {
    //fps
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    //creamos el thread
    private Thread gameThread;

    //crear el jugador
    private Player player;

    public Game() {

        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        //hacemos que gamePanel sea el focus de los
        // inputs que enviamos desde el teclado o el mouse
        //de lo contrario nuestros inputs no serán leidos correctamente
        gamePanel.requestFocus();



        startGameLoop();

    }

    private void initClasses() {
        player = new Player(200,200);
    }

    //iniciar game loop (thread)
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        player.update();
    }

    public void render(Graphics g){
        player.render(g);
    }

    // ** runnable del frame rate **
    //al implementar runnable nos genera este metodo
    //en el cual vamos a ingresar el codigo que queremos ejecutar un un thread distinto
    @Override
    public void run() {
        //si vamos a hacer que el juego corra a 120fps vamos a necesitar
        // saber cuanto tiempo durara cada frame (en nanosegundos)
        // 1 segundo = 1 000 000 000 nanosegundos
        double timePerFrame = 1000000000.0 / FPS_SET;
        //updates por segundo
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime =  System.nanoTime();

        //contador de frames
        int frames = 0;
        //contador de actualizaciones
        int updates = 0;
        long lastCheck = System.currentTimeMillis();


        double deltaU = 0;
        double deltaF = 0;


        //aqui tendremos nuestro game loop
        while (true) {

            long currentTime = System.nanoTime();

            //incrementamos deltaU => (tiempo actual - tiempo anterior) / tiempo entre actualizacion
            //al restar dos variables que obtienen su contenido del mismo metodo
            //System.nanoTime() lo que hacemos es asegurarnos que no haya ningún tipo de delay o latencia en el juego

            deltaU += (currentTime - previousTime) / timePerUpdate;
            //lo mismo va para delta Frame
            deltaF += (currentTime - previousTime) / timePerFrame;
            //asignamos el tiempo actual al tiempo previo
            //porque recordemos que su asignación de valor se hace fuera de este bucle
            //de hacer lo contrario su valor nunca se actualizará
            previousTime = currentTime;
            //en caso de haber ms perdidos con este condicional hacemos que se pinte un poquitito antes en pantalla
            //el panel
            if(deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }
            //refrescamos el panel usando la misma teoria del codigo de la linea superior
            //para optimizar la velocidad de refrescado del panel
            if(deltaF >= 1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            //* inicio logica contador fps *


            //si un segundo ha pasado desde el anterior fps check
            //hacemos un nuevo fps check, almecenamos el newFPS check como
            //el lastFPS check y repetimos
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "  | UPS:  "+updates);
                frames = 0;
                updates = 0;
            }

            // * fin logica contador fps *


        }

    }

    public void windowFocusLost(){
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }
}
