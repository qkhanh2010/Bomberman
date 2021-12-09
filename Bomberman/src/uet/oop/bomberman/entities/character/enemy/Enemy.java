package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Direction;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.DirectionExplosion;
import uet.oop.bomberman.entities.bomb.Explosion;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.util.Random;

public abstract class Enemy extends Character {
    protected int vanishTime = 40;
    protected int speed = 1;
    protected int value_navigation=-1;
    protected int time_navigation = 320;
    public Enemy(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        direction = Direction.RIGHT;
    }
    @Override
    public void die() {
        isKilled = true;
    }

    @Override
    protected abstract void afterkilled();

    @Override
    public void update() {
        if (!isKilled) {
            changeanimate();
            this.img = this.sprite.getFxImage();
        } else {
            afterkilled();
            if (vanishTime > 0) {
                vanishTime--;
            } else {
                remove();
            }
        }
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            ((Bomber) entity).die();
            return true;
        }
        if (entity instanceof DirectionExplosion || entity instanceof Explosion) {
            die();
        }
        return true;
    }
    
    public void Ramdom_navigation(String Name_enemy) throws FileNotFoundException {
        Random rd= new Random();
        while (true){
            int i = rd.nextInt(4);
            if(i == 0 && direction != Direction.LEFT && move(Direction.LEFT ,true,Name_enemy)){
                direction = Direction.LEFT;
                return;
            }
            if(i == 1 && direction != Direction.UP && move(Direction.UP ,true,Name_enemy)){
                direction = Direction.UP;
                return;
            }
            if(i == 2 && direction != Direction.RIGHT && move(Direction.RIGHT ,true,Name_enemy)){
                direction = Direction.RIGHT;
                return;
            }
            if(i == 3 && direction != Direction.DOWN && move(Direction.DOWN ,true,Name_enemy)){
                direction = Direction.DOWN;
                return;
            }
        }
    }
    public boolean move(Direction direction, boolean moving, String Name_enemy) throws FileNotFoundException {
        int _x = x, _y = y;
        switch (direction) {
            case UP:
                load_Anh(Direction.UP,Name_enemy);
                if( y % Sprite.SCALED_SIZE !=0) {
                    y -= speed;
                }else {
                    if (moveThrough(x , y - 32)) {
                        y -= speed;
                    } else return false;
                }
                break;
            case RIGHT:
                load_Anh(Direction.RIGHT,Name_enemy);
                if( x %Sprite.SCALED_SIZE != 0) {
                    x +=speed;
                }else {
                    if(moveThrough(x+32,y)){
                        x+=speed;
                    }else return false;
                }
                break;
            case DOWN:
                load_Anh(Direction.DOWN,Name_enemy);
                if( y % Sprite.SCALED_SIZE !=0) {
                    y += speed;
                }else {
                    if (moveThrough(x , y + 32)) {
                        y += speed;
                    } else return false;
                }
                break;
            case LEFT:
                //img = Sprite.balloom_left1.getFxImage();
                load_Anh(Direction.LEFT,Name_enemy);
                if( x %Sprite.SCALED_SIZE != 0) {
                    x -=speed;
                }else {
                    if(moveThrough(x-32,y)){
                        x -=speed;
                    }else return false;
                }
                break;
        }
        return true;
    }
    public void load_Anh(Direction direction,String Name_Enemy){
        if(Name_Enemy.equals("balloom")){
            if(direction == Direction.LEFT || direction == Direction.DOWN){
                if(direction == Direction.LEFT) img = Sprite.balloom_left1.getFxImage();
                setSprite(Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 20));
            }
            else{
                if(direction == Direction.RIGHT) img = Sprite.balloom_right1.getFxImage();
                setSprite(Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 20));
            }
        }
        if(Name_Enemy.equals("oneal")){
            if(direction == Direction.LEFT || direction == Direction.DOWN){
                if(direction == Direction.LEFT) img = Sprite.oneal_left1.getFxImage();
                setSprite(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 20));
            }
            else{
                if(direction == Direction.RIGHT) img = Sprite.oneal_right1.getFxImage();
                setSprite(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 20));
            }
        }
    }
    public boolean moveThrough(int x, int y) {
        int _x = x / Sprite.SCALED_SIZE;
        int _y = y / Sprite.SCALED_SIZE;
        Entity s = BombermanGame.getStillObjects(_x, _y, this);
        if(!s.collide(this)) {
            return false;
        }
        return true;
    }
}
