package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Direction;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    protected void afterkilled() {
        setSprite(Sprite.finalMovingSprite(Sprite.oneal_dead, Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, vanishTime, 40));
    }

    @Override
    public void update() {
        speed = 2;
        changeanimate();
        if (!isKilled) {
            try {
                int i = AI();
                if (i == -1) {
                    value_navigation += 1;
                    if (value_navigation / time_navigation == 1) {
                        Ramdom_navigation("oneal");
                        value_navigation = 0;
                        return;
                    }
                    if (!moveAI(direction, true)) {
                        Ramdom_navigation("oneal");
                    }
                }
                if (i == 0 && moveAI(Direction.LEFT, true)) {
                    direction = Direction.LEFT;
                    if (x % Sprite.SCALED_SIZE != 0) {
                        x -= speed;
                    }
                }
                if (i == 1 && moveAI(Direction.UP, true)) {
                    direction = Direction.UP;
                    if (y % Sprite.SCALED_SIZE != 0) {
                        y -= speed;
                    }
                }
                if (i == 2 && moveAI(Direction.RIGHT, true)) {
                    direction = Direction.RIGHT;
                    if (x % Sprite.SCALED_SIZE != 0) {
                        x += speed;
                    }
                }
                if (i == 3 && moveAI(Direction.DOWN, true)) {
                    direction = Direction.DOWN;
                    if (y % Sprite.SCALED_SIZE != 0) {
                        y += speed;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("E");
            }
        } else {
            afterkilled();
            if (vanishTime > 0) {
                vanishTime--;
            } else {
                remove();
            }
        }
    }


    private int AI() {
        try {
            Scanner scanner = new Scanner(Bomber.index);
            int bomberX = scanner.nextInt();
            int bomberY = scanner.nextInt();
            int _x = x / Sprite.SCALED_SIZE;
            int _y = y / Sprite.SCALED_SIZE;
            if (bomberY == _y && Math.abs(bomberX - _x) < 10) {
                if (bomberX < _x) return 0;
                else if (bomberX > _x) return 2;
            }
            if (bomberX == _x && Math.abs(bomberY - _y) < 5) {
                if (bomberY < _y) return 1;
                else if (bomberY > _y) return 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean moveAI(Direction direction, boolean moving) throws FileNotFoundException {
        int _x = x, _y = y;
        switch (direction) {
            case UP:
                setSprite(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 20));
                if( y % Sprite.SCALED_SIZE !=0) {
                    y -= speed;
                }else {
                    if (!moveThrough(x, y - 32)) {
                        return false;
                    } else {
                        if (x % Sprite.SCALED_SIZE == 0) y -= speed;
                        else return false;
                    }
                }
                break;
            case RIGHT:
                img = Sprite.oneal_right1.getFxImage();
                setSprite(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 20));
                if( x %Sprite.SCALED_SIZE != 0) {
                    x +=speed;
                }else {
                    if (!moveThrough(x + 32, y)) {
                        return false;
                    } else {
                        if (y % Sprite.SCALED_SIZE == 0) x += speed;
                        else return false;
                    }
                }
                break;
            case DOWN:
                setSprite(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 20));
                if( y % Sprite.SCALED_SIZE !=0) {
                    y += speed;
                }else {
                    if (!moveThrough(x, y + 32)) {
                        return false;
                    } else {
                        if (x % Sprite.SCALED_SIZE == 0) y += speed;
                        else return false;
                    }
                }
                break;
            case LEFT:
                img = Sprite.oneal_left1.getFxImage();
                setSprite(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 20));
                if( x %Sprite.SCALED_SIZE != 0) {
                    x -=speed;
                }else {
                    if (!moveThrough(x - 32, y)) {
                        return false;
                    } else {
                        if (y % Sprite.SCALED_SIZE == 0) x -= speed;
                        else return false;
                    }
                }
                break;
        }
        return true;
    }
}

