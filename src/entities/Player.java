package entities;

import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 30; //120fps/6 animaciones por segundo = 20animation speed

    //animaciones desde Constants
    private int playerAction = IDLE_2;

    //MOVIMIENTO

    private boolean left, right, up, down;
    private boolean moving = false, attacking = false, jumping = false;

    private float playerSpeed = 2.0f;

    public Player(float x, float y){
        super(x, y);
        loadAnimations();
    }

    public void update(){

        //valida si hay cambios en el panel y actualiza la posicion de los elementos
        updatePosition();

        updateAnimationTick();

        //validar input y asignar animacion correspondiente
        setAnimation();


    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][animationIndex], (int)x, (int)y, 100, 74, null);
    }

    //manejar el tiempo entre cada imagen para generar la animacion
    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed){
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)){
                animationIndex = 0;
                attacking = false;
                jumping = false;
            }
        }
    }

    private void setAnimation() {

        int startAnimation = playerAction;

        if(moving){
            playerAction = RUNNING;
        }
        else{
            playerAction = IDLE_2;
        }

        if(attacking){
            playerAction = ATTACK_1;
        }

        if(jumping){
            playerAction = JUMP;
        }

        if(startAnimation != playerAction){
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {

        moving = false;

        if(left && !right){
            x -= playerSpeed;
            moving = true;
        }
        else if(!left && right){
            x += playerSpeed;
            moving = true;
        }

        if(up && !down){
            y -= playerSpeed;
            moving = true;
        }
        else if(!up && down){
            y += playerSpeed;
            moving = true;
        }
    }

    private void loadAnimations() {
            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            //asignamos un espacio en el array dependiendo de las imagenes que compongan nuestra animacion
            animations = new BufferedImage[12][10];

            for(int j = 0; j < animations.length; j++){
                for(int i = 0; i < animations[j].length; i++){
                    animations[j][i] = img.getSubimage(i*50,j*37,50,37);
                }
            }
    }

    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    //getters y setters para movimiento (up, down, left, right)

    public void setAttack(boolean attacking){
        this.attacking = attacking;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
