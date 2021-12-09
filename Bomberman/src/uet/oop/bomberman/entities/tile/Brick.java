package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.DirectionExplosion;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean destroyed = false;
    private int vanishTime = 20;

    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (destroyed) {
            setSprite(Sprite.finalMovingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, vanishTime, 20 ));
            if (vanishTime > 0) {
                vanishTime--;
            } else {
                remove();
            }
        }
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof DirectionExplosion) {
            destroyed = true;
        }

        return false;
    }
}
