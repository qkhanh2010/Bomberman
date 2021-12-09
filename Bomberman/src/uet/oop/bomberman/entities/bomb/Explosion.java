package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.Direction;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends Bomb {
    protected Direction direction;
    protected boolean lastEx = false;

    public Explosion(int xUnit, int yUnit, Direction direction, boolean lastEx, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        this.direction = direction;
        this.lastEx = lastEx;
        switch (direction) {
            case UP:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, vanishTime, 20));
                }
                break;
            case DOWN:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, vanishTime, 20));
                }
                break;
            case RIGHT:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, vanishTime, 20));
                }
                break;
            case LEFT:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, vanishTime, 20));
                }
                break;
        }
    }

    @Override
    public void update() {
        chooseSprite();
        if (vanishTime > 0) {
            vanishTime--;
        } else {
            remove();
        }
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Character) {
            ((Character) entity).die();
        }
        return true;
    }

    public void timeCount() {
        if (vanishTime > 0) {
            vanishTime--;
        } else {
            _remove = true;
        }
    }

    private void chooseSprite() {
        switch (direction) {
            case UP:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, vanishTime, 20));
                }
                break;
            case DOWN:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, vanishTime, 20));
                }
                break;
            case RIGHT:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, vanishTime, 20));
                }
                break;
            case LEFT:
                if (lastEx) {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, vanishTime, 20));
                } else {
                    setSprite(Sprite.finalMovingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, vanishTime, 20));
                }
                break;
        }
    }
}
