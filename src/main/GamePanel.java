package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private Game game;
    public GamePanel(Game game) {

        this.game = game;

        //implemento la clase KeyboardInputs la cual
        //a su vez implementa la interfaz de KeyListener, esto nos sirve
        // para alivianar codigo en nuestra clase y hacerlo más manejable
        // --SE LE PASA ESTE PANEL PARA OBTENER Y USARLOS EN EL CONTROL EL XDELTA Y YDELTA
        addKeyListener(new KeyboardInputs(this));

        //añadimos los inputs del mouse
        //inicializamos la clase MouseInputs
        //igual que con el keyboard se le manda este panel para generar el movimiento
        mouseInputs = new MouseInputs(this);

        //input botones mouse
        addMouseListener(mouseInputs);

        //input movimiento y drag
        addMouseMotionListener(mouseInputs);

        //tamaño panel
        setPanelSize();;
    }

    private void setPanelSize(){
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    //actualizar panel

    public void paintComponent(Graphics g) {
        //invocamos a la super class para evitar bugs y glitches visuales al actualizar el panel
        super.paintComponent(g);

        game.render(g);

    }

    public Game getGame(){
        return game;
    }

}
