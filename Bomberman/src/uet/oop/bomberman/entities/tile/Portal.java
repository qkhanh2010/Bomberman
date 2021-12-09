package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;

public class Portal extends Tile {

    public Portal(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity entity) {
        if (BombermanGame.enemies.isEmpty()) {
            if (entity instanceof Bomber) {
                BombermanGame.level++;

                try {
                    BombermanGame.loadMap(BombermanGame.level);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }
}
