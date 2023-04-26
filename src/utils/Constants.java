package utils;

public class Constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE_1 = 0;
        public static final int RUNNING = 1;
        public static final int IDLE_2 = 2;
        public static final int ATTACK_1 = 3;
        public static final int ATTACK_2 = 4;
        public static final int ATTACK_3 = 5;
        public static final int JUMP = 6;
        public static final int FALLING = 7;
        public static final int HIT = 8;
        public static final int GROUND = 9;
        public static final int DIE = 10;
        public static final int RUNNING_BACKWARDS = 11;

        public static final int ATTACK_JUMP_1 = 12;
        public static final int ATTACK_JUMP_2 = 13;


        public static int GetSpriteAmount(int player_action){

            switch (player_action){
                case RUNNING:
                case ATTACK_2:
                case ATTACK_3:
                case RUNNING_BACKWARDS:
                    return 6;
                case IDLE_1:
                case IDLE_2:
                case GROUND:
                    return  4;
                case ATTACK_1:
                    return  5;
                case JUMP:
                    return 10;
                case FALLING:
                    return 2;
                case HIT:
                    return 3;
                case DIE:
                    return 7;
                default:
                    return 1;
            }

        }

    }
}
