package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        // creacion de la ventana jframe
        jframe = new JFrame();


        //asignacioón de cierre por defecto
        jframe.setDefaultCloseOperation(3);

        //añadimos el panel al frame
        jframe.add(gamePanel);

        //asignar posicion en la que aparecerá la ventana en este caso será en el centro de la pantalla
        jframe.setLocationRelativeTo(null);

        jframe.setResizable(false);

        //ajustar el tamaño de la ventana al tamaño de su contenido
        jframe.pack();

        //confirgurar visibilidad de la pantalla
        jframe.setVisible(true);
        //el metodo setVisible() siempre debe ir al final del bloque de codigo
        // para evitar que ocurran bugs inexperados

        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
