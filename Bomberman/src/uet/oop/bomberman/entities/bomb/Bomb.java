package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity {
    protected boolean canMoveThrough = true;
    protected int explodeTime = 120;
    protected int vanishTime = 20;
    protected boolean exploded = false;
    protected int explosionRange;

    protected DirectionExplosion[] directionExplosions = new DirectionExplosion[4];

    public Bomb(int xUnit, int yUnit, Sprite sprite, int explosionRange) {
        super(xUnit, yUnit, sprite);
        this.explosionRange = explosionRange;
        setSprite(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60));
    }

    public Bomb(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
    }

    @Override
    public void update() {
        timeCount();
        if(!exploded) {
            setSprite(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60));
            changeanimate();
        } else {
            setSprite(Sprite.finalMovingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, vanishTime, 20 ));
            updateExplosion();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
        if (exploded) {
            for (DirectionExplosion i : directionExplosions) {
                i.render(gc);
            }
        }
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            int x = entity.getX();
            int y = entity.getY();
            int _x = entity.getX() + 20;
            int _y = entity.getY() + 30;
            int a = this.x;
            int b = this.y;
            int _a = this.x + 20;
            int _b = this.y + 30;
            if (x >= _a || _x < a || y >= _b || _y < b) {
                canMoveThrough = false;
            }
            return canMoveThrough;
        }
        if (entity instanceof DirectionExplosion) {
            explodeTime = 0;
//            explode();
            return false;
        }
        return false;
    }

    public void timeCount() {
        if (explodeTime > 0) {
            explodeTime--;
        } else {
            if (!exploded) {
                explode();
            } else {
                setSprite(Sprite.finalMovingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, vanishTime, 20 ));
            }
            if (vanishTime > 0) {
                vanishTime--;
            } else {
                _remove = true;
            }
        }
    }

    protected void explode() {
        canMoveThrough = true;
        exploded = true;
        explodeTime = 0;
        Entity character = BombermanGame.getStillObjects(getxUnit(), getyUnit(), this);
        if (character != null && character instanceof Character) {
            ((Character) character).die();
        }
        Direction direction;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: direction = Direction.UP; break;
                case 1: direction = Direction.DOWN; break;
                case 2: direction = Direction.RIGHT; break;
                case 3: direction = Direction.LEFT; break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }
            directionExplosions[i] = new DirectionExplosion(getxUnit(), getyUnit(), sprite, direction, explosionRange);
        }
    }

    private void updateExplosion() {
        for (DirectionExplosion i : directionExplosions) {
            i.update();
        }
    }
}
