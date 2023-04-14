package inputs;

import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    //se recibe el panel al cual se le aplicará el movimiento por mouse listener y mouse motion
    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    //MOUSE INPUTS LISTENERS
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //MOUSE MOTION LISTENERS

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Dragging the mouse");
    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }
}
