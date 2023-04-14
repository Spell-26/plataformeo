package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    //estas variables almacenarán las posiciones del cursor en 2 dimensiones x, y
    //ahora se necesitará una forma de modificarlo
    private float xDelta = 100, yDelta = 100;

    //fps counter
    private int frames = 0;
    private long lastCheck = 0;

    //buffer image
    private BufferedImage img;

    //idle animation
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 30; //120fps/6 animaciones por segundo = 20animation speed

    //animaciones desde Constants
    private int playerAction = JUMP;

    //MOVIMIENTO
    private int playerDirection = -1;
    private boolean moving = false;



    public GamePanel() {

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

        //cargar el sprite map del heroe
        importImg();

        //cargar animaciones
        loadAnimations();
    }

    private void loadAnimations() {
        //asignamos un espacio en el array dependiendo de las imagenes que compongan nuestra animacion
        animations = new BufferedImage[11][10];

        for(int j = 0; j < animations.length; j++){

            for(int i = 0; i < animations[j].length; i++){

                animations[j][i] = img.getSubimage(i*50,j*37,50,37);

            }
        }


    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/heroe/heroe_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize(){
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

public void setDirection(int direction){
        this.playerDirection = direction;
        moving = true;
}
public void setMoving(boolean moving){
        this.moving = moving;
}


    //manejar en tiempo entre cada imagehn para generar la animacion
    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed){
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)){
                animationIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if(moving){
            playerAction = RUNNING;
        }
        else{
            playerAction = IDLE_2;
        }
    }
    private void updatePosition() {
        if(moving){
            switch (playerDirection){
                case LEFT :
                    xDelta -=5;
                    break;
                case UP :
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g) {
        //invocamos a la super class para evitar bugs y glitches visuales al actualizar el panel
        super.paintComponent(g);

        updateAnimationTick();

        //validar input y asignar animacion correspondiente
        setAnimation();
        //valida si hay cambios en el panel y actualiza la posicion de los elementos
        updatePosition();

        //dibujamos la imagen que es traida mas arriba y almacenada en la variable img
        //usamos el metodo .getSubImage() para traer solo una sección de la imagen

        g.drawImage(animations[playerAction][animationIndex], (int)xDelta, (int)yDelta, 200, 148, null);
    }




}
