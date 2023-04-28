package entities;

import main.Game;
import utils.HelpMethods;
import utils.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 30; //120fps/6 animaciones por segundo = 20animation speed

    //animaciones desde Constants
    private int playerAction = IDLE_2;

    //MOVIMIENTO

    private boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false, jumping = false;

    //jumping / gravity
    private float playerSpeed = 2.0f;
    private float airSpeed = 0f;
    private float gravity = 0.04f  * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f  * Game.SCALE;
    private boolean inAir = false;

    // LVL DATA
    private int[][] lvlData;

    // HITBOX
    private float xDrawOffset = 17 * Game.SCALE;
    private float yDrawOffset = 9 * Game.SCALE;

    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 15 * Game.SCALE, 26 * Game.SCALE);
    }

    public void update(){

        //valida si hay cambios en el panel y actualiza la posicion de los elementos
        updatePosition();

        updateAnimationTick();

        //validar input y asignar animacion correspondiente
        setAnimation();


    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        //drawHitbox(g);
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

        if(inAir){
            if(airSpeed < 0){
                playerAction = JUMP;
            }
            else{
                playerAction = FALLING;
            }
        }

        if(attacking){
            playerAction = ATTACK_1;
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

        if(jumping){
            jump();
        }

        if(!left && !right && !inAir){
            return;
        }

        float xSpeed = 0;

        if(left){
            xSpeed -= playerSpeed;
        }
        if(right) {
            xSpeed += playerSpeed;
        }
        if(!inAir){
            if(!HelpMethods.isEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }
        //jump movement
        if(inAir){
            if(HelpMethods.CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else{
                hitbox.y = HelpMethods.GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0){
                    resetInAir();
                }else{
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }else{
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if(inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(HelpMethods.CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;
        }else{
            hitbox.x = HelpMethods.GetEntityXPosNextToWall(hitbox, xSpeed);
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

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!HelpMethods.isEntityOnFloor(hitbox, lvlData)){
            inAir = true;
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
