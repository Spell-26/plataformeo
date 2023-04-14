package main;

public class Game implements Runnable {
    //fps
    private final int FPS_SET = 120;
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    //creamos el thread
    private Thread gameThread;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        //hacemos que gamePanel sea el focus de los
        // inputs que enviamos desde el teclado o el mouse
        //de lo contrario nuestros inputs no serán leidos correctamente
        gamePanel.requestFocus();

        startGameLoop();

    }

    //iniciar game loop (thread)
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
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

        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();


        //aqui tendremos nuestro game loop
        while (true) {
            //nanosegundos actuales
            now = System.nanoTime();
            //checkeamos desde el frame previo si la duración ha pasado (timePerFrame)
            //si lo ha hecho generamos el siguiente frame

            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            //* inicio logica contador fps *


            //si un segundo ha pasado desde el anterior fps check
            //hacemos un nuevo fps check, almecenamos el newFPS check como
            //el lastFPS check y repetimos
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }

            // * fin logica contador fps *


        }

    }
}
